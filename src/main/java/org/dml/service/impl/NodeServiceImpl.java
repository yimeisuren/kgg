package org.dml.service.impl;

import org.dml.dao.NodeRepository;
import org.dml.entities.Node;
import org.dml.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

// @Transactional
@Service
public class NodeServiceImpl implements NodeService {
    @Autowired
    private NodeRepository nodeRepository;

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Autowired
    SetOperations<String, String> setOperations;

    @Autowired
    SetOperations<String, String> opsForSet;


    @Override
    public int addNode(Node node) {

        // 在neo4j中保存节点
        // TODO: 1、此处调用的是spring框架提供的方法，在插入之前会先match一次，速度会有所降低
        //       2、假如保存的节点中带着关系，而关系的另一端节点不存在，那么没有办法将
        //          另一端节点的id正确添加到redis中
        //      3、relationship中存在嵌套情况，如果存在一个很长的依赖关系，会出现问题

        // 将标签label到id的映射保存到redis中
        for (String label : node.getLabels()) {
            setOperations.add(label, String.valueOf(node.getId()));
        }

        // 此处node的id还没有获取到
        Node nodeWithId = nodeRepository.save(node);
        // TODO: 可以返回插入节点的id
        return 1;
    }

    @Override
    public int addNodes(Collection<Node> nodes) {
        // redis中建立label到id映射
        for(Node node : nodes){
            for (String label : node.getLabels()) {
                setOperations.add(label, String.valueOf(node.getId()));
            }
        }
        List<Node> nodeList = nodeRepository.saveAll(nodes);
        // TODO: 可以返回插入节点的id
        return nodes.size();
    }


    @Override
    public int deleteNodeById(Long id) {
        // TODO: 这里会在neo4j中查询两次，后续考虑实现一个返回删除节点的delete方法
        //       貌似删除节点后无法返回节点
        //       或者将第一次查询节点信息的操作转移到redis中

        Node node = findNodeById(id);

        if(node != null) {
            // redis中删除该节点的标签到节点的映射
            for (String label : node.getLabels()) {
                setOperations.remove(label, String.valueOf(id));
            }

            // neo4j中删除节点
            nodeRepository.deleteById(id);
            return 1;
        }

        return 0;
    }

    @Override
    public int deleteNodesByIds(Collection<Long> ids) {
        int result = 0;
        for (Long id : ids) {
            result += deleteNodeById(id);
        }
        return result;
    }
    public int deleteNodesByIds(Long... ids) {
        int result = 0;
        for (Long id : ids) {
            result += deleteNodeById(id);
        }

        return result;
    }

    @Override
    public int deleteNodesByLabel(String label) {
        Set<String> ids = opsForSet.members(label);

        redisTemplate.delete(label);
//        opsForSet.remove(CustomRedisKey.NODE, ids);
//        for(String id : ids) {
//            opsForSet.remove(CustomRedisKey.NODE, id);
//        }
//        nodeRepository.deleteAllById(idsL);

        // 如果为空，redis的返回值是一个空集合，不会出现空指针的情况
        Set<Long> idsL = ids.stream()
                .map(Long::parseLong)
                .collect(Collectors.toSet());
        // 使用实现的通过id删除节点的方法, 实现对关联标签的删除
        return deleteNodesByIds(idsL);
    }


    @Override
    public int deleteNodesByLabels(Collection<String> labels) {
        Set<String> ids = opsForSet.intersect(labels);
//        for (String label : labels) {
//            opsForSet.remove(label, ids);
//        }
//        nodeRepository.deleteAllById(idsL);
//        opsForSet.remove(CustomRedisKey.NODE, ids);

        // 如果为空，redis的返回值是一个空集合，不会出现空指针的情况
        Set<Long> idsL = ids.stream()
                .map(Long::parseLong)
                .collect(Collectors.toSet());

        // 使用实现的通过id删除节点的方法, 实现对关联标签的删除
        return deleteNodesByIds(idsL);
    }


    @Override
    public Node findNodeById(Long id) {
        // TODO: 这里结果为空时，返回null
        return nodeRepository.findById(id).orElse(null);
    }

    @Override
    public List<Node> findNodesByIds(Collection<Long> ids) {
        ArrayList<Node> nodes = new ArrayList<>();
        for (Long id : ids) {
            nodes.add(findNodeById(id));
        }
        return nodes;
    }

    @Override
    public List<Node> findNodesByLabel(String label) {
        // 不采用neo4j中的实现, 而是使用redis进行辅助实现
        return findNodesByLabels(Collections.singletonList(label));
    }

    @Override
    public List<Node> findNodesByLabels(Collection<String> labels) {
        // redis中取交集
        Set<String> ids = setOperations.intersect(labels);
        // ids.forEach(System.out::println);

        // 如果为空，redis的返回值是一个空集合，不会出现空指针的情况
        Set<Long> idsL = ids.stream()
                .map(Long::parseLong)
                .collect(Collectors.toSet());

        return findNodesByIds(idsL);
    }




    // TODO: 目前的更新节点方式只能实现如下功能
    //      1、添加节点的label，redis中数据也会更新
    //      2、删除节点的label，但redis中不会同步更新
    //      3、添加或修改节点的属性，但不能删除属性
    //   后续的修改方案应该是按照功能细分更新节点功能，从而完善此功能
    //   添加边的功能目前感觉不应该放到这里
    //
    @Override
    public int updateNode(Node node) {
        return addNode(node);
    }

    @Override
    public int updateNodes(Collection<Node> nodes) {
        return addNodes(nodes);
    }

    @Override
    public void clearDB() {
        redisTemplate.getConnectionFactory().getConnection().flushAll();
        nodeRepository.clearDB();
    }
}
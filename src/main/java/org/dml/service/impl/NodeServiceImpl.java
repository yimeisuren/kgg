package org.dml.service.impl;

import org.dml.constance.CustomRedisKey;
import org.dml.dao.NodeRepository;
import org.dml.entities.Node;
import org.dml.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;

import java.util.*;

// @Transactional
@Service
public class NodeServiceImpl implements NodeService {
    @Autowired
    NodeRepository nodeRepository;

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Autowired
    SetOperations<String, String> setOperations;

    @Autowired
    SetOperations<String, String> opsForSet;


    @Override
    public int addNode(Node node) {
        // 将id保存到redis中
        setOperations.add(CustomRedisKey.NODE, node.getId());

        // 将标签label到id的映射保存到redis中
        for (String label : node.getLabels()) {
            setOperations.add(label, node.getId());
        }

        // 在neo4j中保存节点
        nodeRepository.save(node);

        return 1;
    }

    @Override
    public int addNodes(Collection<Node> nodes) {
        int result = 0;

        for (Node node : nodes) {
            result += addNode(node);
        }

        return result;
    }

    @Override
    public int deleteNodeById(String id) {
        Node node = findNodeById(id);

        // redis中删除该节点关于标签到节点的映射
        for (String label : node.getLabels()) {
            setOperations.remove(label, id);
        }
        // redis中删除该节点的id
        setOperations.remove(CustomRedisKey.NODE, id);

        // neo4j中删除节点
        nodeRepository.deleteById(id);
        return 1;
    }

    @Override
    public int deleteNodesByIds(Collection<String> ids) {
        int result = 0;

        for (String id : ids) {
            result += deleteNodeById(id);
        }

        return result;
    }

    public int deleteNodesByIds(String... ids) {
        int result = 0;

        for (String id : ids) {
            result += deleteNodeById(id);
        }

        return result;
    }

    //TODO: 改
    @Override
    public int updateNode(Node node) {
        return addNode(node);
    }

    @Override
    public int updateNodes(Collection<Node> nodes) {
        return addNodes(nodes);
    }

    @Override
    public Node findNodeById(String id) {
        //redis中未发现该值, 说明数据库中不存在
        if (Boolean.FALSE.equals(setOperations.isMember(CustomRedisKey.NODE, id))) {
            return null;
        }
        return nodeRepository.findById(id).get();
    }

    @Override
    public List<Node> findNodesByIds(Collection<String> ids) {
        ArrayList<Node> nodes = new ArrayList<>();
        for (String id : ids) {
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
        ids.forEach(System.out::println);
        return findNodesByIds(ids);
    }

    @Override
    public int deleteNodesByLabel(String label) {
        Set<String> ids = opsForSet.members(label);
        redisTemplate.delete(label);
        nodeRepository.deleteAllById(ids);
        return ids.size();
    }


    @Override
    public int deleteNodesByLabels(Collection<String> labels) {
        Set<String> ids = opsForSet.intersect(labels);
        for (String label : labels) {
            opsForSet.remove(label, ids);
        }

        nodeRepository.deleteAllById(ids);
        return 0;
    }

    @Override
    public void clearDB() {
        nodeRepository.clearDB();
    }
}

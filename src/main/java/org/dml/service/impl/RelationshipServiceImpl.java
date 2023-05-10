package org.dml.service.impl;

import org.dml.dao.RelationshipRepository;
import org.dml.entities.Node;
import org.dml.entities.Relationship;
import org.dml.service.NodeService;
import org.dml.service.RelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public class RelationshipServiceImpl implements RelationshipService {
    @Autowired
    NodeService nodeService;

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Autowired
    SetOperations<String, String> setOperations;

    @Autowired
    RelationshipRepository relationshipRepository;
    @Override
    public void addRelationship(Long fromId, Long toId, String type) {
        Node nodeFrom = nodeService.findNodeById(fromId);
        Node nodeTo = nodeService.findNodeById(toId);
        if(nodeFrom != null && nodeTo != null){
            Relationship relationship = new Relationship();
            relationship.setTo(nodeTo);
            relationship.setType(type);
            nodeFrom.addRelationship(relationship);
            nodeService.addNode(nodeFrom);
        }
    }

    @Override
    public void addRelationship(Long fromId, Long toId, String type, Map<String, Object> attributes) {
        Node nodeFrom = nodeService.findNodeById(fromId);
        Node nodeTo = nodeService.findNodeById(toId);
        if(nodeFrom != null && nodeTo != null){
            Relationship relationship = new Relationship();
            relationship.setTo(nodeTo);
            relationship.setType(type);
            relationship.setAttributes(attributes);
            nodeFrom.addRelationship(relationship);
            nodeService.addNode(nodeFrom);
        }
    }

    @Override
    public int deleteRelationshipById(Long id) {
        relationshipRepository.customizedDeleteById(id);
        return 1;
    }

    @Override
    public int deleteRelationshipByIds(Long... ids) {
        for(Long id : ids){
            deleteRelationshipById(id);
        }
        return 0;
    }

    @Override
    public int deleteRelationshipByIds(Collection<Long> ids) {
        for(Long id : ids){
            deleteRelationshipById(id);
        }
        return 0;
    }

    // TODO：这里可以使用redis辅助实现
    @Override
    public int deleteRelationshipByType(String type) {

        return 0;
    }

    @Override
    public Relationship findRelationshipById(Long id) {
        return relationshipRepository.customizedFindById(id).orElse(null);
    }

    // TODO：这里可以使用redis辅助实现
    @Override
    public List<Relationship> findRelationshipsByType(String type) {
        return null;
    }


//    @Override
//    public void addRelationship(String fromId, String toId, String id, String label) {
//        // neo4j中属性不会直接被删除, 所以对于需要修改的节点to, 可以直接new Node
//        // 但是由于from节点需要添加, 如果直接new, 那么outs列表中最多只会保留一个值, 因为每次new都会创建一个新对象, 其列表会覆盖原有的列表
//        Node from = nodeService.findNodeById(fromId);
//        from = (from != null) ? from : new Node(fromId);
//        nodeService.updateNode(from);
//
//        Node to = nodeService.findNodeById(toId);
//        to = (to != null) ? to : new Node(toId);
//        nodeService.updateNode(to);
//
//        from.addOutRelationship(label, id);
//
//        // 开始保存边
//        Relationship relationship = new Relationship(id, label, from, to);
//        // 保存到redis中
//        redisTemplate.opsForSet().add(CustomRedisKey.RELATIONSHIP, id);
//        redisTemplate.opsForSet().add(CustomRedisKey.RELATIONSHIP_LABEL + label, id);
//        relationshipRepository.save(relationship);
//    }
//
//    /**
//     * TODO: 删除的时候是否可以先不删除Redis中对应标签中的id?
//     * 需要在findRelationship中考虑如果查找的是一个不存在的id会有什么影响
//     *
//     * @param id
//     * @return
//     */
//    @Override
//    public int deleteRelationshipById(String id) {
//        Relationship relationship = relationshipRepository.findById(id).get();
//        String label = relationship.getLabel();
//
//        // 删除relationship时需要更新from节点中的out
//        // TODO: out中设计成保存to节点的id还是设计成保存relationship的id需要考虑, 也许保存relationship的id可以提供更强大的功能, 方便后面的子图查询?
//        Node from = relationship.getFrom();
//        // String toId = relationship.getTo().getId();
//        from.deleteOutRelationship(label, id);
//        nodeService.updateNode(from);
//
//        // 删除Neo4j中的节点
//        relationshipRepository.deleteById(id);
//        // 删除Redis中对应标签中的id
//        setOperations.remove(label, id);
//        return 1;
//    }
//
//    @Override
//    public int deleteRelationshipByIds(String... ids) {
//        for (String id : ids) {
//            deleteRelationshipById(id);
//        }
//        return ids.length;
//    }
//
//    @Override
//    public int deleteRelationshipByIds(Collection<String> ids) {
//        for (String id : ids) {
//            deleteRelationshipById(id);
//        }
//        return ids.size();
//    }
//
//    @Override
//    public int deleteRelationshipByLabel(String label) {
//        Set<String> ids = setOperations.members(label);
//        return deleteRelationshipByIds(ids);
//    }
//
//    @Override
//    public Relationship findRelationshipById(String id) {
//        return relationshipRepository.findById(id).get();
//    }
//
//    @Override
//    public List<Relationship> findRelationshipsByLabel(String label) {
//        Set<String> ids = setOperations.members(CustomRedisKey.RELATIONSHIP_LABEL + label);
//        return relationshipRepository.findAllById(ids);
//    }
}
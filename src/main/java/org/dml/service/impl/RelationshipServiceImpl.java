package org.dml.service.impl;

import org.dml.constance.CustomRedisKey;
import org.dml.dao.RelationshipRepository;
import org.dml.entities.Node;
import org.dml.entities.Relationship;
import org.dml.service.NodeService;
import org.dml.service.RelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RelationshipServiceImpl implements RelationshipService {
    @Autowired
    NodeService nodeService;

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Autowired
    RelationshipRepository relationshipRepository;

    @Override
    public void addRelationship(String fromId, String toId, String id, String label) {
        // neo4j中属性不会直接被删除, 所以对于需要修改的节点to, 可以直接new Node
        // 但是由于from节点需要添加, 如果直接new, 那么outs列表中最多只会保留一个值, 因为每次new都会创建一个新对象, 其列表会覆盖原有的列表
        Node from = nodeService.findNodeById(fromId);
        from = (from != null) ? from : new Node(fromId);
        from.addOutRelationship(label, id);
        nodeService.updateNode(from);

        Node to = new Node(toId);
        nodeService.updateNode(to);

        // 开始保存边
        Relationship relationship = new Relationship(id, label, from, to);
        // 保存到redis中
        redisTemplate.opsForSet().add(CustomRedisKey.RELATIONSHIP, id);
        redisTemplate.opsForSet().add(CustomRedisKey.RELATIONSHIP_LABEL + label, id);
        relationshipRepository.save(relationship);
    }
}

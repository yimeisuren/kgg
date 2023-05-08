package org.dml.dao;

import org.dml.constance.CustomRedisKey;
import org.dml.entities.Node;
import org.dml.entities.Relationship;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Optional;

@SpringBootTest
class KggApplicationRelationshipRepositoryTests {


    @Autowired
    RelationshipRepository relationshipRepository;

    @Autowired
    NodeRepository nodeRepository;

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    /**
     * 直接保存会将其作为节点进行保存, 是不是应该限制不能直接添加关系, 也就是说对于关系的操作是受限的
     */
    @Test
    public void relationshipSaveTest() {
        Node node1 = new Node("node_200");
        Node node2 = new Node("node_201");

        Relationship relationship = new Relationship("relationship_22", "交流", node1, node2);

        relationshipRepository.save(relationship);
    }

    @Test
    public void relationshipQueryByIdTest() {
        if (Boolean.FALSE.equals(redisTemplate.opsForSet().isMember(CustomRedisKey.RELATIONSHIP, "relationship_22"))) {
            return;
        }
        Optional<Relationship> relationshipOptional = relationshipRepository.findById("relationship_22");
        Relationship relationship = relationshipOptional.get();
        System.out.println(relationship);
    }

}

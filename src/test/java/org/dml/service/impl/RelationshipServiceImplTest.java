package org.dml.service.impl;

import org.dml.entities.Relationship;
import org.dml.service.RelationshipService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
class RelationshipServiceImplTest {
    @Autowired
    RelationshipService relationshipService;

    @Test
    void addRelationship() {
        relationshipService.addRelationship("node_200", "node_201", "relationship_22", "交流");
        relationshipService.addRelationship("node_200", "node202", "relationship_23", "交流");
        relationshipService.addRelationship("node_200", "node_203", "relationship_24", "学习");
    }

    @Test
    public void findRelationshipByIdTest() {
        Relationship relationship = relationshipService.findRelationshipById("1002");
        System.out.println(relationship);
    }

    @Test
    public void findRelationshipByIdsTest() {
        List<Relationship> relationships = relationshipService.findRelationshipsByLabel("参战");
        relationships.forEach(System.out::println);
    }


    @Test
    public void deleteRelationshipByIdTest() {
        relationshipService.deleteRelationshipByIds("1001", "1002");
    }
}
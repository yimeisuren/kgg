package org.dml.service.impl;

import org.dml.dao.NodeRepository;
import org.dml.dao.RelationshipRepository;
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

    @Autowired
    RelationshipRepository relationshipRepository;

    @Autowired
    NodeRepository nodeRepository;

    @Test
    void addRelationship() {
        relationshipService.addRelationship(10L, 10L, "交流");
        relationshipService.addRelationship(10L, 11L, "交流");
        relationshipService.addRelationship(10L, 11L,  "学习");
    }

    @Test
    public void findRelationshipByIdTest() {
        Relationship relationship = relationshipRepository.customizedFindById(59L).orElse(null);
        System.out.println(relationship);
    }

    @Test
    public void findRelationshipByIdsTest() {
        List<Relationship> relationships = relationshipService.findRelationshipsByType("参战");
        relationships.forEach(System.out::println);
    }


    @Test
    public void deleteRelationshipByIdTest() {
        relationshipService.deleteRelationshipByIds(10L, 12L);
    }
}
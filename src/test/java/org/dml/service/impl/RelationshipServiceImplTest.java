package org.dml.service.impl;

import org.dml.service.RelationshipService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


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
}
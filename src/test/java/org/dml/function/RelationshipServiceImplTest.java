package org.dml.function;

import org.dml.service.RelationshipService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RelationshipServiceImplTest {
    @Autowired
    RelationshipService relationshipService;



    @Test
    void addRelationshipTest() {
        relationshipService.addRelationship("10", "12", "1001", "参战");
        relationshipService.addRelationship("10", "14", "1002", "参战");
        relationshipService.addRelationship("12", "14", "1003", "对抗");
    }
}

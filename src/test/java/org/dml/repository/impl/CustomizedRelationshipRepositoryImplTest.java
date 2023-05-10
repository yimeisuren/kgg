package org.dml.repository.impl;

import org.dml.dao.RelationshipRepository;
import org.dml.entities.Relationship;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author : yangzhuo
 */
@SpringBootTest
public class CustomizedRelationshipRepositoryImplTest {
    @Autowired
    RelationshipRepository relationshipRepository;

    @Test
    void findRelationById(){
        Relationship relationship = relationshipRepository.customizedFindById(59L).orElse(null);
        System.out.println(relationship);
    }

    @Test
    void deleteRelationById(){
        relationshipRepository.customizedDeleteById(-100001087L);
    }

    @Test
    void deleteRelationByType(){
        relationshipRepository.customizedDeleteByType("战斗");
    }

    @Test
    void countByType(){
        System.out.println(relationshipRepository.customizedCountByType("Relationship"));
    }
}

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

    /**
     * TODO:
     * <p>
     * 1. 需要体现出节点之间的依赖关系, 不应该存在孤立的节点
     * <p>
     * 2. label是不是应该对其进行管理限制, 例如一个label的管理系统, 先添加label, 不能让任意一个字符串作为label, 难以规范化, 会造成后面的诸多麻烦
     */
    @Test
    void addRelationship() {
        relationshipService.addRelationship("10", "12", "1001", "参战");
        relationshipService.addRelationship("10", "14", "1002", "参战");
        relationshipService.addRelationship("12", "14", "1003", "对抗");
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
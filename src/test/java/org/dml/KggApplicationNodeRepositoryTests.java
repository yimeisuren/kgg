package org.dml;

import org.dml.dao.NodeRepository;
import org.dml.dao.RelationshipRepository;
import org.dml.entities.Node;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@SpringBootTest
class KggApplicationNodeRepositoryTests {


    @Autowired
    RelationshipRepository relationshipRepository;

    @Autowired
    NodeRepository nodeRepository;


    @Autowired
    RedisTemplate<String, String> redisTemplate;




    private void entitySave(Node node) {
        // 保存节点中label到id的映射
        for (String label : node.getLabels()) {
            redisTemplate.opsForSet().add(label, node.getId());
        }

        nodeRepository.save(node);
    }


    @Test
    public void entitySaveTest() {

        Node from = new Node();
        from.setId("from");
        entitySave(from);
    }

    @Test
    public void entitySavaWithLabelsTest() {
        Node node = new Node();
        node.setId("to");
        node.addLabel("人");
        node.addLabel("学生");

        node.addAttribute("name", "张三");
        node.addAttribute("age", "18");
        node.addAttribute("email", "123456@qq.com");

        entitySave(node);
    }


    private void entityDelete(Node node) {
        for (String label : node.getLabels()) {
            redisTemplate.opsForSet().remove(label, node.getId());
        }
        nodeRepository.delete(node);
    }


    /**
     * 通过id来查询实体
     */
    @Test
    public void entityQueryByIdTest() {
        Optional<Node> optionalEntity = nodeRepository.findById("-2");
        Node node = optionalEntity.get();
        System.out.println(node);
    }

    /**
     * 通过标签来查询实体
     */
    @Test
    public void entityQueryByLabelTest() {
        // Node node03 = new Node();
        // node03.setId("testNode03");
        // node03.setLabels(Arrays.asList("人", "学生"));
        // entitySave(node03);
        //
        // Node node04 = new Node();
        // node04.setId("testNode04");
        // node04.setLabels(Arrays.asList("人", "教师"));
        // entitySave(node04);

        // List<Node> nodes = entityRepository.findNodesByLabelsContains("人");
        List<Node> nodes = nodeRepository.findNodesByLabelsContains("人");
        nodes.forEach(System.out::println);
    }


    @Test
    public void entityQueryByFlagTest() {
        Set<String> studentsId = redisTemplate.opsForSet().members("student");
        studentsId.forEach(System.out::println);
        List<Node> entities = nodeRepository.findAllById(studentsId);
        entities.forEach(System.out::println);
    }

    @Test
    public void entityQueryByFlagsTest() {
        List<String> flags = Arrays.asList("student", "worker");
        // redis中取交集
        Set<String> result = redisTemplate.opsForSet().intersect(flags);
        result.forEach(System.out::println);
        List<Node> entities = nodeRepository.findAllById(result);
        entities.forEach(System.out::println);
    }

    @Test
    public void removeLabelTest(){
    }

    // @Test
    // public void relationshipQueryByIdTest() {
    //     Relationship edge = relationshipRepository.findByName("AB");
    //     System.out.println(edge);
    // }


    // @Test
    // public void graphSaveTest() {
    //     Node teacherA = new Node();
    //     teacherA.setId("teacherA");
    //     teacherA.addLabel("teacher");
    //     teacherA.addAttribute("name", "teacherA");
    //     redisTemplate.opsForSet().add("entity", "teacherA");
    //
    //     Node studentB = new Node();
    //     studentB.setId("studentB");
    //     studentB.addLabel("student");
    //     studentB.addAttribute("name", "studentB");
    //     redisTemplate.opsForSet().add("entity", "studentB");
    //
    //     Node studentC = new Node();
    //     studentC.setId("studentC");
    //     studentC.addLabel("student");
    //     studentC.addAttribute("name", "studentC");
    //     redisTemplate.opsForSet().add("entity", "studentC");
    //
    //     Node teacherD = new Node();
    //     teacherD.setId("teacherD");
    //     teacherD.addLabel("teacher");
    //     teacherD.addAttribute("name", "teacherD");
    //     redisTemplate.opsForSet().add("entity", "teacherD");
    //
    //     Relationship edgeAB = new Relationship("AB", teacherA, studentB);
    //     edgeAB.setLabel("teach");
    //     Relationship edgeAC = new Relationship("AC", teacherA, studentC);
    //     edgeAC.setLabel("teach");
    //     Relationship edgeDB = new Relationship("DB", teacherD, studentB);
    //     edgeDB.setLabel("teach");
    //     Relationship edgeDC = new Relationship("DC", teacherD, studentC);
    //     edgeDC.setLabel("teach");
    //     Relationship edgeBC = new Relationship("BC", studentB, studentC);
    //     edgeBC.setLabel("discuss");
    //     Relationship edgeCB = new Relationship("CB", studentC, studentB);
    //     edgeCB.setLabel("discuss");
    //
    //     SetOperations<String, String> opsForSet = redisTemplate.opsForSet();
    //     opsForSet.add("entity_" + edgeAB.getFrom().getId(), edgeAB.getTo().getId());
    //     opsForSet.add("entity_" + edgeAC.getFrom().getId(), edgeAC.getTo().getId());
    //     opsForSet.add("entity_" + edgeBC.getFrom().getId(), edgeBC.getTo().getId());
    //     opsForSet.add("entity_" + edgeCB.getFrom().getId(), edgeCB.getTo().getId());
    //     opsForSet.add("entity_" + edgeDB.getFrom().getId(), edgeDB.getTo().getId());
    //     opsForSet.add("entity_" + edgeDC.getFrom().getId(), edgeDC.getTo().getId());
    //
    //     if (Boolean.FALSE.equals(opsForSet.isMember("relationship", edgeAB.getName()))) {
    //         teacherA.addNeighbour(edgeAB);
    //         opsForSet.add("relationship", edgeAB.getName());
    //     }
    //     if (Boolean.FALSE.equals(opsForSet.isMember("relationship", edgeAC.getName()))) {
    //         teacherA.addNeighbour(edgeAC);
    //         opsForSet.add("relationship", edgeAC.getName());
    //     }
    //     if (Boolean.FALSE.equals(opsForSet.isMember("relationship", edgeBC.getName()))) {
    //         studentB.addNeighbour(edgeBC);
    //         opsForSet.add("relationship", edgeBC.getName());
    //     }
    //     if (Boolean.FALSE.equals(opsForSet.isMember("relationship", edgeCB.getName()))) {
    //         studentC.addNeighbour(edgeCB);
    //         opsForSet.add("relationship", edgeCB.getName());
    //     }
    //     if (Boolean.FALSE.equals(opsForSet.isMember("relationship", edgeDB.getName()))) {
    //         teacherD.addNeighbour(edgeDB);
    //         opsForSet.add("relationship", edgeDB.getName());
    //     }
    //     if (Boolean.FALSE.equals(opsForSet.isMember("relationship", edgeDC.getName()))) {
    //         teacherD.addNeighbour(edgeDC);
    //         opsForSet.add("relationship", edgeDC.getName());
    //     }
    //
    //     entityRepository.save(teacherA);
    //     entityRepository.save(teacherD);
    //
    //     // Graph graph = new Graph();
    //     // graph.addEdge(edgeAB);
    //     // graph.addEdge(edgeAC);
    //     // graph.addEdge(edgeBC);
    // }


}

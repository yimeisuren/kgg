package org.dml.service.impl;

import org.dml.entities.Node;
import org.dml.service.NodeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;


@SpringBootTest
class NodeServiceImplTest {
    @Autowired
    NodeService nodeService;


    @Test
    void addNode() {
        Node node = new Node();

        // id没有实际意义, 只为了作为标志, 应该随机生成?
        node.setId("10");

        // node添加多个标签
        node.addLabel("战役");

        // name会作为节点的占位符进行显示
        node.addAttribute("name", "勒班陀战役");
        node.addAttribute("winner", "神圣同盟舰队");
        node.addAttribute("time", "1571-10-07");

        nodeService.addNode(node);
    }


    @Test
    public void findByLabelTest() {
        List<Node> nodes = nodeService.findNodesByLabel("战役");
        nodes.forEach(System.out::println);
    }


    @Test
    void addNodes() {
        Node node1 = new Node();
        node1.setId("service_11");
        node1.addLabel("service");
        node1.addAttribute("level", "service");
        node1.addAttribute("name", "张三");
        node1.addAttribute("age", "18");


        Node node2 = new Node();
        node2.setId("service_12");
        node2.addLabel("service");
        node2.addAttribute("level", "service");
        node2.addAttribute("name", "张三");
        node2.addAttribute("age", "18");

        nodeService.addNodes(Arrays.asList(node1, node2));
    }

    @Test
    void deleteNodeById() {
        nodeService.deleteNodeById("11");
        nodeService.deleteNodeById("12");
        nodeService.deleteNodeById("13");
        nodeService.deleteNodeById("14");
    }

    @Test
    void deleteNodesByIds() {
        nodeService.deleteNodesByIds(Arrays.asList("11", "12"));
    }

    @Test
    public void deleteNodesByLabel() {
        int counts = nodeService.deleteNodesByLabel("桨帆船");
        System.out.println(counts);
    }

    @Test
    void updateNode() {
        Node node = new Node();
        node.setId("service_1");
        node.addLabel("service");
        node.addAttribute("name", "李四");

        nodeService.updateNode(node);
    }

    @Test
    void updateNodes() {
        Node student = new Node();
        student.setId("service_2");
        student.addLabel("service");
        student.addLabel("people");
        student.addLabel("student");
        student.addAttribute("name", "李四");

        Node teacher = new Node();
        teacher.setId("service_3");
        teacher.addLabel("service");
        teacher.addLabel("people");
        teacher.addLabel("teacher");
        teacher.addAttribute("name", "王五");

        nodeService.updateNodes(Arrays.asList(student, teacher));
    }

    @Test
    void findNodeById() {
        Node node = nodeService.findNodeById("service_1");
        System.out.println(node);
    }

    /**
     * 测试查询一个不存在的节点
     */
    @Test
    public void findNodeById2Test() {
        Node node = nodeService.findNodeById("node_10000");
        System.out.println(node);
    }

    @Test
    void findNodesByIds() {
        List<Node> nodes = nodeService.findNodesByIds(Arrays.asList("service_1", "service_2"));
        nodes.forEach(System.out::println);
    }

    @Test
    void findNodesByLabel() {
        List<Node> nodes = nodeService.findNodesByLabel("service");
        nodes.forEach(System.out::println);
    }

    @Test
    void findNodesByLabels() {
        List<Node> nodes = nodeService.findNodesByLabels(Arrays.asList("people", "student"));
        nodes.forEach(System.out::println);
    }
}
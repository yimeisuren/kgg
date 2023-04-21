package org.dml;

import org.dml.entities.Node;
import org.dml.service.NodeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
class KggApplicationNodeServiceTests {

    @Autowired
    NodeService nodeService;


    @Test
    public void addNodeTest() {
        Node node = new Node();

        node.setId("service_1");

        node.addLabel("service");

        node.addAttribute("level", "service");
        node.addAttribute("name", "张三");
        node.addAttribute("age", "18");

        nodeService.addNode(node);
    }

    @Test
    public void addNodesTest() {
        Node node2 = new Node();
        node2.setId("service_2");
        node2.addLabel("service");
        node2.addAttribute("level", "service");
        node2.addAttribute("name", "张三");
        node2.addAttribute("age", "18");


        Node node3 = new Node();
        node3.setId("service_3");
        node3.addLabel("service");
        node3.addAttribute("level", "service");
        node3.addAttribute("name", "张三");
        node3.addAttribute("age", "18");

        nodeService.addNodes(Arrays.asList(node2, node3));
    }
}

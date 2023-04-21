package org.dml.function;

import org.dml.entities.Node;
import org.dml.service.NodeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class NodeServiceFunctionTest {
    @Autowired
    NodeService nodeService;


    @Test
    void addNode11() {
        Node node = new Node();

        // id没有实际意义, 只为了作为标志, 应该随机生成?
        node.setId("11");

        // node添加多个标签
        node.addLabels("战船", "桨帆船");
        // TODO: 设计一个默认的name机制, 让用户勾选, 如果不输入name, 那么自动选择第一个label作为其name
        node.addAttribute("name", "桨帆船");
        node.addAttribute("owner", "神圣同盟舰队");
        node.addAttribute("counts", "206");

        nodeService.addNode(node);
    }

    @Test
    void addNode12() {
        Node node = new Node();

        node.setId("12");

        // node添加多个标签
        node.addLabels("战船", "加莱赛战船");

        node.addAttribute("name", "加莱赛战船");
        node.addAttribute("owner", "神圣同盟舰队");
        node.addAttribute("counts", "6");

        nodeService.addNode(node);
    }


    @Test
    void addNode13() {
        Node node = new Node();

        // id没有实际意义, 只为了作为标志, 应该随机生成?
        node.setId("13");

        // node添加多个标签
        node.addLabels("战船", "桨帆船");

        node.addAttribute("name", "桨帆船");
        node.addAttribute("owner", "威尼斯舰队");
        node.addAttribute("counts", "109");

        nodeService.addNode(node);
    }

    @Test
    void addNode14() {
        Node node = new Node();

        node.setId("14");

        // node添加多个标签
        node.addLabels("战船", "加莱赛战船");

        node.addAttribute("name", "加莱赛战船");
        node.addAttribute("owner", "威尼斯舰队");
        node.addAttribute("counts", "6");

        nodeService.addNode(node);
    }
}

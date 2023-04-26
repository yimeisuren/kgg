package org.dml.service.impl;

import org.dml.entities.Node;
import org.dml.service.NodeService;
import org.junit.jupiter.api.Test;
import org.neo4j.driver.internal.value.StringValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;


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
    public void addNodesTest() {
        Node node10 = new Node();
        // id没有实际意义, 只为了作为标志, 应该随机生成?
        node10.setId("10");
        // node添加多个标签
        node10.addLabel("战役");
        // name会作为节点的占位符进行显示
        node10.addAttribute("name", "勒班陀战役");
        node10.addAttribute("winner", "神圣同盟舰队");
        node10.addAttribute("time", "1571-10-07");


        Node node11 = new Node();
        node11.setId("11");
        node11.addLabels("战船", "桨帆船");
        // TODO: 设计一个默认的name机制, 让用户勾选, 如果不输入name, 那么自动选择第一个label作为其name
        node11.addAttribute("name", "桨帆船");
        node11.addAttribute("owner", "神圣同盟舰队");
        node11.addAttribute("counts", "206");


        Node node12 = new Node();
        node12.setId("12");
        node12.addLabels("战船", "加莱赛战船");
        node12.addAttribute("name", "加莱赛战船");
        node12.addAttribute("owner", "神圣同盟舰队");
        node12.addAttribute("counts", "6");

        Node node13 = new Node();
        node13.setId("13");
        node13.addLabels("战船", "桨帆船");
        node13.addAttribute("name", "桨帆船");
        node13.addAttribute("owner", "威尼斯舰队");
        node13.addAttribute("counts", "109");

        Node node14 = new Node();
        node14.setId("14");
        node14.addLabels("战船", "加莱赛战船");
        node14.addAttribute("name", "加莱赛战船");
        node14.addAttribute("owner", "威尼斯舰队");
        node14.addAttribute("counts", "6");

        nodeService.addNodes(Arrays.asList(node10, node11, node12, node13, node14));
    }


    // @Test
    // void addNodes() {
    //     Node node1 = new Node();
    //     node1.setId("service_11");
    //     node1.addLabel("service");
    //     node1.addAttribute("level", "service");
    //     node1.addAttribute("name", "张三");
    //     node1.addAttribute("age", "18");
    //
    //
    //     Node node2 = new Node();
    //     node2.setId("service_12");
    //     node2.addLabel("service");
    //     node2.addAttribute("level", "service");
    //     node2.addAttribute("name", "张三");
    //     node2.addAttribute("age", "18");
    //
    //     nodeService.addNodes(Arrays.asList(node1, node2));
    // }

    @Test
    void deleteNodeById() {
        nodeService.deleteNodeById("10");
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

    //TODO: bug
    // 用StringValue替换掉String可以, 但这种解决方法总感觉不伦不类
    @Test
    void updateNode() {

        Node node = nodeService.findNodeById("10");
        System.out.println(node);

        Map<String, Set<String>> outs = node.getOuts();
        // TODO: 实际上这里得到的是Set<StringValue>类型的集合,
        //  1. 原因一: 泛型擦除
        //  2. 原因二: 没有为@CompositeProperty注解修饰的Map<String, Set<String>>类型的outs添加自定义转换器（这是一种解决方案, 但是不会, 想要参考默认的转换器, 但没看明白）
        //  3. 为什么不使用Map<String, Set<StringValue>>来修饰outs呢? 这样每次在添加String的时候都需要包装一层, 嫌麻烦
        Set<String> fightSet = outs.get("参战");
        StringValue[] values = fightSet.toArray(new StringValue[0]);
        for (StringValue value : values) {
            String s = value.asString();
            System.out.println(s);
        }
        fightSet.remove(new StringValue("1001"));

        for (Object s : fightSet) {
            System.out.println(s);
        }

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

    @Test
    public void findByLabelTest() {
        List<Node> nodes = nodeService.findNodesByLabel("战役");
        nodes.forEach(System.out::println);
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


    @Test
    public void updateNodeTest() {

    }

    @Test
    public void clearDBTest() {

        nodeService.clearDB();
    }
}
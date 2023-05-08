package org.dml.dao;

import org.dml.entities.NodeX;
import org.dml.entities.RelationshipX;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
public class NodeXRepositoryTest {
    @Autowired
    private NodeXRepository nodeXRepository;


    // @Test
    // public void addNodesTest() {
    //     Node node10 = new Node();
    //     // id没有实际意义, 只为了作为标志, 应该随机生成?
    //     node10.setId(10L);
    //     // node添加多个标签
    //     node10.addLabel("战役");
    //     // name会作为节点的占位符进行显示
    //     node10.addAttribute("name", "勒班陀战役");
    //     node10.addAttribute("winner", "神圣同盟舰队");
    //     node10.addAttribute("time", "1571-10-07");
    //
    //
    //     Node node11 = new Node();
    //     node11.setId(11L);
    //     node11.addLabels("战船", "桨帆船");
    //     // TODO: 设计一个默认的name机制, 让用户勾选, 如果不输入name, 那么自动选择第一个label作为其name
    //     node11.addAttribute("name", "桨帆船");
    //     node11.addAttribute("owner", "神圣同盟舰队");
    //     node11.addAttribute("counts", "206");
    //
    //
    //     Node node12 = new Node();
    //     node12.setId(12L);
    //     node12.addLabels("战船", "加莱赛战船");
    //     node12.addAttribute("name", "加莱赛战船");
    //     node12.addAttribute("owner", "神圣同盟舰队");
    //     node12.addAttribute("counts", "6");
    //
    //     Node node13 = new Node();
    //     node13.setId(13L);
    //     node13.addLabels("战船", "桨帆船");
    //     node13.addAttribute("name", "桨帆船");
    //     node13.addAttribute("owner", "威尼斯舰队");
    //     node13.addAttribute("counts", "109");
    //
    //     Node node14 = new Node();
    //     node14.setId(14L);
    //     node14.addLabels("战船", "加莱赛战船");
    //     node14.addAttribute("name", "加莱赛战船");
    //     node14.addAttribute("owner", "威尼斯舰队");
    //     node14.addAttribute("counts", "6");
    //
    //     Relationship relationship = new Relationship();
    //     relationship.setTo(node11);
    //     relationship.setLabel("战斗");
    //     relationship.addAttribute("time", "1999-02-10");
    //
    //     List<Relationship> relationships = new ArrayList<>();
    //     relationships.add(relationship);
    //     Map<String, List<Relationship>> relationshipM = new HashMap<>();
    //     relationshipM.put(relationship.getLabel(), relationships);
    //     node10.setRelationships(relationshipM);
    //
    //
    //     nodeService.addNodes(Arrays.asList(node10, node12, node13, node14));
    // }


    @Test
    public void saveAllTest() {
        NodeX node1 = new NodeX();

        node1.addAttribute("name", "root");

        nodeXRepository.save(node1);
    }

    /**
     * 保存节点
     */
    @Test
    public void saveTest() {
        NodeX from = new NodeX();
        from.addAttribute("name", "老师1");
        from.addAttribute("age", "30");

        NodeX nodeX = nodeXRepository.save(from);
        System.out.println("nodeX = " + nodeX);
    }

    /**
     * 查询节点
     */
    @Test
    public void findByIdTest() {
        NodeX from = nodeXRepository.findById(300L).get();
        System.out.println("from = " + from);

        NodeX to = nodeXRepository.findById(200L).get();
        System.out.println("to = " + to);

        RelationshipX r1 = new RelationshipX("R", to);
        from.addRelationship(r1);

        NodeX saveFrom = nodeXRepository.save(from);
        System.out.println("saveFrom = " + saveFrom);
    }

    /**
     * 测试循环依赖
     */
    @Test
    public void findById2Test() {
        NodeX from = nodeXRepository.findById(400L).get();
        System.out.println("from = " + from);

        NodeX to = nodeXRepository.findById(500L).get();
        System.out.println("to = " + to);

        RelationshipX r1 = new RelationshipX("R", to);
        from.addRelationship(r1);

        RelationshipX r2 = new RelationshipX("R", from);
        to.addRelationship(r2);

        nodeXRepository.save(from);
        nodeXRepository.save(to);

        // 员工A    ->  所属公司  ->  阿里巴巴
        // 阿里巴巴  ->  拥有员工  ->  员工A, ...
        NodeX saveFrom = nodeXRepository.findById(400L).get();
        System.out.println("saveFrom = " + saveFrom);
    }

    @Test
    public void addNodeTest() {
        NodeX node1 = new NodeX();
        node1.addAttribute("name", "node1");

        NodeX node2 = new NodeX();
        node2.addAttribute("name", "node2");

        NodeX node3 = new NodeX();
        node3.addAttribute("name", "node3");

        nodeXRepository.save(node1);
        nodeXRepository.save(node2);
        nodeXRepository.save(node3);
    }


    @Test
    public void addRelationshipTest() {
        NodeX node4 = nodeXRepository.findById(0L).get();
        System.out.println("node4 = " + node4);

        NodeX node5 = nodeXRepository.findById(20L).get();
        System.out.println("node5 = " + node5);

        NodeX node6 = nodeXRepository.findById(21L).get();
        System.out.println("node6 = " + node6);

        RelationshipX r4 = new RelationshipX("R", node4);
        r4.addAttribute("name", "user");
        RelationshipX r5 = new RelationshipX("R", node5);
        r5.addAttribute("name", "admin");
        RelationshipX r6 = new RelationshipX("R", node6);
        r6.addAttribute("name", "root");

        RelationshipX r6Update = new RelationshipX("R", node6);
        r6Update.addAttribute("name", "rootUpdate");
        // TODO: 期待r6Update这条关系会替换掉原来的, 但实际效果HashSet会保留最初插入的那个对象(对象A和对象B, 在equals层面上对象A和对象B相等, 先后调用对象A和对象B, 实际HashSet中保留的是哪个对象)
        // 确实如预期所料,
        node4.addRelationship(r5);
        node4.addRelationship(r6);
        node4.addRelationship(r6Update);

        node5.addRelationship(r6);
        node6.addRelationship(r4);

        nodeXRepository.save(node4);
        nodeXRepository.save(node5);
        nodeXRepository.save(node6);
    }


    /**
     * 尝试更新边的属性, 同时不重复
     */
    @Test
    public void save2Test() {
        NodeX node4 = nodeXRepository.findById(5L).get();
        NodeX node6 = nodeXRepository.findById(6L).get();

        RelationshipX r6Final = new RelationshipX("Relationship", node6);
        r6Final.addAttribute("name", "r5-r6");
        node4.addRelationship(r6Final);

        nodeXRepository.save(node4);
    }

    @Test
    public void findById3Test() {
        NodeX from = nodeXRepository.findById(5L).get();
        Map<String, List<RelationshipX>> relationships = from.getRelationships();
        relationships.forEach((key, values) -> {
            System.out.println("{");
            System.out.println("key = " + key);
            System.out.println("value=");
            values.forEach(System.out::println);
            System.out.println("}");
        });

        System.out.println("from = " + from);
    }

    /**
     * 删除节点
     */
    @Test
    public void deleteByIdTest() {
        nodeXRepository.deleteById(400L);
    }
}

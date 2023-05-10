package org.dml.service.impl;

import org.dml.dao.NodeRepository;
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

    @Autowired
    private NodeRepository nodeRepository;

    @Test
    public void clearDBTest() {
        nodeService.clearDB();
    }

    @Test
    void addNode() {
        Node node = new Node();

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
        node10.setId(11L);
        // node添加多个标签
        node10.addLabel("战役");
        // name会作为节点的占位符进行显示
        node10.addAttribute("name", "勒班陀战役");
        node10.addAttribute("winner", "神圣同盟舰队");
        node10.addAttribute("time", "1571-10-07");


        Node node11 = new Node();
        node11.setId(11L);
        node11.addLabels("战船", "桨帆船");
        // TODO: 设计一个默认的name机制, 让用户勾选, 如果不输入name, 那么自动选择第一个label作为其name
        node11.addAttribute("name", "桨帆船");
        node11.addAttribute("owner", "神圣同盟舰队");
        node11.addAttribute("counts", "206");


        Node node12 = new Node();
        node12.setId(12L);
        node12.addLabels("战船", "加莱赛战船");
        node12.addAttribute("name", "加莱赛战船");
        node12.addAttribute("owner", "神圣同盟舰队");
        node12.addAttribute("counts", "6");

        Node node13 = new Node();
        node13.setId(13L);
        node13.addLabels("战船", "桨帆船");
        node13.addAttribute("name", "桨帆船");
        node13.addAttribute("owner", "威尼斯舰队");
        node13.addAttribute("counts", "109");

        Node node14 = new Node();
        node14.setId(14L);
        node14.addLabels("战船", "加莱赛战船");
        node14.addAttribute("name", "加莱赛战船");
        node14.addAttribute("owner", "威尼斯舰队");
        node14.addAttribute("counts", "6");

//        Relationship relationship = new Relationship();
//        relationship.setTo(node11);
//        relationship.setLabel("战斗");
//        relationship.addAttribute("time", "1999-02-10");
//
//        List<Relationship> relationships = new ArrayList<>();
//        relationships.add(relationship);
//        Map<String, List<org.dml.entities.Relationship>> relationshipM = new HashMap<>();
//        relationshipM.put(relationship.getLabel(), relationships);
//        node10.setRelationships(relationshipM);


        nodeService.addNodes(Arrays.asList(node10, node11, node12, node13, node14));
    }


    @Test
    void deleteNodeById() {
        nodeRepository.deleteById(22L);
        //nodeService.deleteNodeById(22L);
        //nodeService.deleteNodeById(21L);
    }

    @Test
    void deleteNodesByIds() {
        int counts =  nodeService.deleteNodesByIds(Arrays.asList(23L, 24L, 25L));
        System.out.println(counts);
    }

    @Test
    public void deleteNodesByLabel() {
        int counts = nodeService.deleteNodesByLabel("战船");
        System.out.println(counts);
    }
    @Test
    public void deleteNodesByLabels() {
        int counts = nodeService.deleteNodesByLabels(Arrays.asList("战船", "桨帆船"));
        System.out.println(counts);
    }

    @Test
    void findNodeById() {
        Node node = nodeRepository.findById(7L).get();
        System.out.println(node.getRelationships());
        //Node node = nodeService.findNodeById(17L);
        System.out.println(node);
    }

    @Test
    public void findNodeByLabelTest() {
        List<Node> nodes = nodeService.findNodesByLabel("战役");
        nodes.forEach(System.out::println);
    }

    @Test
    public void findNodeByLabelsTest() {
        List<Node> nodes = nodeService.findNodesByLabels(Arrays.asList("战船", "加莱赛战船"));
        nodes.forEach(System.out::println);
    }


    //  TODO: update方法仍需完善
    @Test
    void updateNode() {

        Node node = nodeService.findNodeById(21L);
        System.out.println(node);

//        Map<String, List<Relationship>> outs = node.getRelationships();
//
//        List<Relationship> fightSet = outs.get("战斗");
//        for(Relationship relationship : fightSet){
//            System.out.println(relationship);
//        }

        node.deleteAttribute("time");
        nodeService.updateNode(node);

        node = nodeService.findNodeById(21L);
        System.out.println(node);
    }

    /**
     * 用于快速测试，先删除数据，再添加数据
     */
    @Test
    void deleteAndAdd(){
        nodeService.clearDB();
        addNodesTest();
    }

}
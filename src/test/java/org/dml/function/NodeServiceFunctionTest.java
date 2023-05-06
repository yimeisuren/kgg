package org.dml.function;

import org.dml.entities.Node;
import org.dml.service.NodeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@SpringBootTest
public class NodeServiceFunctionTest {
    @Autowired
    NodeService nodeService;

    @Test
    void addNode10Copy() {
        Node node = new Node();

        // 测试重复id插入是否成功
        node.setId("-2");

        // node添加多个标签
        node.addLabel("战斗");

        // name会作为节点的占位符进行显示
        node.addAttribute("name", "勒班陀战役plus");

        nodeService.addNode(node);
    }

    @Test
    void addNode10() {
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

    /**
     * 生成一些没有实际意义的用于性能测试的数据
     */


    @Test
    void mockData() throws IOException {
        // TODO: 下面是通过apoc导出生成的数据文件格式, 难道导入还需要进行修改吗?
        // _id,_labels,attributes.name,attributes.time,attributes.winner,id
        // 30800,:Node:战役,勒班陀战役1,1571-10-07,神圣同盟舰队,10

        // CALL apoc.import.csv("file:///your_csv_file.csv",
        // {
        //    nullValues:[''],
        //    delimiter:',',
        //    arrayDelimiter:';',
        //    stringIds:true,
        //    create:true,
        //    dateFormat:'dd/MM/yyyy',
        //    timeFormat:'HH:mm:ss',
        //    ignoreDuplicateNodes:true,
        //    ignoreExtraColumns:true,
        //    skip:0, limit:-1,
        //    mapping:{
        //      column1:{type:'String', array:false, nodeProperty:false},
        //      column2:{type:'Integer', array:false, nodeProperty:true},
        //      column3:{type:'Float', array:false, nodeProperty:true},
        //      column4:{type:'String', array:true, nodeProperty:false}}});
        for (int i = 0; i < 10; i++) {
            String path = "D:\\Coding\\DB\\neo4j-community-3.5.35\\import\\node-apoc-" + i + ".csv";
            File file = new File(path);
            FileWriter fileWriter = new FileWriter(file, false);
            fileWriter.write("id:ID,:LABEL,attributes.name:STRING,attributes.time:STRING,attributes.winner:STRING\n");
            StringBuilder builder = new StringBuilder();
            for(int j = i * 2000000; j < (i+1)* 2000000; ++j) {
                String s = builder.append(j).append(",")
                        .append("战役;海战,")
                        .append("战役").append(j).append(",")
                        .append("1571-10-07,")
                        .append("神圣同盟舰队").append("\n")
                        .toString();
                fileWriter.write(s);
                builder.delete(0, builder.capacity());
            }
            fileWriter.close();
        }
    }
}

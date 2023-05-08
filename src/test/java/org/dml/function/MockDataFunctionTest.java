package org.dml.function;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@SpringBootTest
public class MockDataFunctionTest {
    private static final int COUNTS = 1000000;
    private static final int FILE_NUMBER = 20;

    private static final int NODE_COUNT = 2000_0000;
    private static final int RELATIONSHIP_COUNT = 1_0000_0000;
    // apoc方式导入生成的数据
    // CALL apoc.import.csv(
    //   [
    //   {fileName: 'file:/node-apoc-0.csv', labels: ['Node']},
    //   {fileName: 'file:/node-apoc-1.csv', labels: ['Node']},
    //   {fileName: 'file:/node-apoc-2.csv', labels: ['Node']},
    //   {fileName: 'file:/node-apoc-3.csv', labels: ['Node']},
    //   {fileName: 'file:/node-apoc-4.csv', labels: ['Node']},
    //   {fileName: 'file:/node-apoc-5.csv', labels: ['Node']},
    //   {fileName: 'file:/node-apoc-6.csv', labels: ['Node']},
    //   {fileName: 'file:/node-apoc-7.csv', labels: ['Node']},
    //   {fileName: 'file:/node-apoc-8.csv', labels: ['Node']},
    //   {fileName: 'file:/node-apoc-9.csv', labels: ['Node']},
    //   {fileName: 'file:/node-apoc-10.csv', labels: ['Node']},
    //   {fileName: 'file:/node-apoc-11.csv', labels: ['Node']},
    //   {fileName: 'file:/node-apoc-12.csv', labels: ['Node']},
    //   {fileName: 'file:/node-apoc-13.csv', labels: ['Node']},
    //   {fileName: 'file:/node-apoc-14.csv', labels: ['Node']},
    //   {fileName: 'file:/node-apoc-15.csv', labels: ['Node']},
    //   {fileName: 'file:/node-apoc-16.csv', labels: ['Node']},
    //   {fileName: 'file:/node-apoc-17.csv', labels: ['Node']},
    //   {fileName: 'file:/node-apoc-18.csv', labels: ['Node']},
    //   {fileName: 'file:/node-apoc-19.csv', labels: ['Node']}
    //   ],
    //   [
    //   {fileName: 'file:/relationship-apoc-0.csv', type: 'R'},
    //   {fileName: 'file:/relationship-apoc-1.csv', type: 'R'},
    //   {fileName: 'file:/relationship-apoc-2.csv', type: 'R'},
    //   {fileName: 'file:/relationship-apoc-3.csv', type: 'R'},
    //   {fileName: 'file:/relationship-apoc-4.csv', type: 'R'},
    //   {fileName: 'file:/relationship-apoc-5.csv', type: 'R'},
    //   {fileName: 'file:/relationship-apoc-6.csv', type: 'R'},
    //   {fileName: 'file:/relationship-apoc-7.csv', type: 'R'},
    //   {fileName: 'file:/relationship-apoc-8.csv', type: 'R'},
    //   {fileName: 'file:/relationship-apoc-9.csv', type: 'R'},
    //   {fileName: 'file:/relationship-apoc-10.csv', type: 'R'},
    //   {fileName: 'file:/relationship-apoc-11.csv', type: 'R'},
    //   {fileName: 'file:/relationship-apoc-12.csv', type: 'R'},
    //   {fileName: 'file:/relationship-apoc-13.csv', type: 'R'},
    //   {fileName: 'file:/relationship-apoc-14.csv', type: 'R'},
    //   {fileName: 'file:/relationship-apoc-15.csv', type: 'R'},
    //   {fileName: 'file:/relationship-apoc-16.csv', type: 'R'},
    //   {fileName: 'file:/relationship-apoc-17.csv', type: 'R'},
    //   {fileName: 'file:/relationship-apoc-18.csv', type: 'R'},
    //   {fileName: 'file:/relationship-apoc-19.csv', type: 'R'}
    //   ],
    //   {}
    // );


    /**
     * 生成一些没有实际意义的用于性能测试的数据
     */
    @Test
    void mockNodeDataApocTest() throws IOException {
        // TODO: 下面是通过apoc导出生成的数据文件格式, 难道导入还需要进行修改吗?
        // _id,_labels,attributes.name,attributes.time,attributes.winner,id
        // 30800,:Node:战役,勒班陀战役1,1571-10-07,神圣同盟舰队,10


        // 1. 所有的节点作为Node是有必要的吗? 感觉可以减轻导入的负担
        // 2. 关系需要按标签类别生成多个文件, 一个关系对应一个文件


        for (int i = 0; i < FILE_NUMBER; i++) {
            String path = "D:\\Coding\\DB\\neo4j-community-3.5.35\\import\\node-apoc-" + i + ".csv";
            File file = new File(path);
            FileWriter fileWriter = new FileWriter(file, false);
            fileWriter.write("id:ID,:LABEL,attributes.name:STRING,attributes.time:STRING,attributes.winner:STRING\n");
            StringBuilder builder = new StringBuilder();
            for (int j = i * COUNTS; j < (i + 1) * COUNTS; ++j) {
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


    @Test
    public void mockRelationshipDataApocTest() throws Exception {
        for (int i = 0; i < FILE_NUMBER; i++) {
            String path = "D:\\Coding\\DB\\neo4j-community-3.5.35\\import\\relationship-apoc-" + i + ".csv";
            File file = new File(path);
            FileWriter fileWriter = new FileWriter(file, false);
            fileWriter.write(":START_ID,:END_ID,year:INT,month:INT\n");
            StringBuilder builder = new StringBuilder();
            for (int j = i * COUNTS + 2; j < (i + 1) * COUNTS; j++) {
                for (int k = 1; k <= 2; ++k) {
                    builder
                            .append(j).append(",")
                            .append(j - k).append(",")
                            .append("2023").append(",")
                            .append("5")
                            .append("\n");
                }
                fileWriter.write(builder.toString());
                builder.delete(0, builder.capacity());
            }
            fileWriter.close();
        }
    }


    /**
     * 重点使用
     *
     * @throws Exception
     */
    @Test
    public void mockDataNeo4jAdminImportTest() throws Exception {
        String nodePath = "D:\\Coding\\DB\\neo4j-community-3.5.35\\import\\node-3.csv";
        String relationshipPath = "D:\\Coding\\DB\\neo4j-community-3.5.35\\import\\relationship-3.csv";
        File nodeFile = new File(nodePath);
        File relationshipFile = new File(relationshipPath);
        FileWriter nodeFileWriter = new FileWriter(nodeFile, false);
        FileWriter relationshipFileWriter = new FileWriter(relationshipFile, false);
        nodeFileWriter.write(":ID,:LABEL,attributes.name:STRING,attributes.time:STRING,attributes.winner:STRING\n");
        relationshipFileWriter.write(":START_ID,:END_ID,:TYPE,fromId:Long,toId:Long,attributes.name:STRING,type:STRING\n");

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < NODE_COUNT; ++i) {
            builder
                    .append(i).append(",")
                    .append("Node").append(",")
                    .append("node").append(i).append(",")
                    .append("2000-11-11").append(",")
                    .append("神圣同盟舰队")
                    .append("\n");
            nodeFileWriter.write(builder.toString());
            builder.delete(0, builder.capacity());
        }

        for (int i = 5; i < NODE_COUNT; i++) {
            for (int j = 1; j <= 5; j++) {
                builder
                        .append(i).append(",")
                        .append(i - j).append(",")
                        .append("Relationship").append(",")
                        .append(i).append(",")
                        .append(i - j).append(",")
                        .append("r").append(i).append("-").append("r").append(i - j).append(",")
                        .append("Relationship")
                        .append("\n");
            }
            relationshipFileWriter.write(builder.toString());
            builder.delete(0, builder.capacity());
        }

        nodeFileWriter.close();
        relationshipFileWriter.close();
    }


    @Test
    public void mockDataNeo4jAdminImport2Test() throws Exception {
        String nodePath = "D:\\Coding\\DB\\neo4j-community-3.5.35\\import\\node-2.csv";
        // String relationshipPath = "D:\\Coding\\DB\\neo4j-community-3.5.35\\import\\relationship.csv";
        File nodeFile = new File(nodePath);
        // File relationshipFile = new File(relationshipPath);
        FileWriter nodeFileWriter = new FileWriter(nodeFile, false);
        // FileWriter relationshipFileWriter = new FileWriter(relationshipFile, false);
        nodeFileWriter.write(":ID,:LABEL,attributes.name:STRING,attributes.time:STRING,attributes.winner:STRING\n");
        // relationshipFileWriter.write(":START_ID,:END_ID,:TYPE,label,attributes.year:INT,attributes.month:INT\n");

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < NODE_COUNT; ++i) {
            builder
                    .append(i).append(",")
                    .append("Node;战役;海战").append(",")
                    .append("战役").append(i).append(",")
                    .append("1571-10-07").append(",")
                    .append("神圣同盟舰队")
                    .append("\n");
            nodeFileWriter.write(builder.toString());
            builder.delete(0, builder.capacity());
        }

        // for (int i = 5; i < NODE_COUNT; i++) {
        //     for (int j = 1; j <= 5; j++) {
        //         builder
        //                 .append(i).append(",")
        //                 .append(i - j).append(",")
        //                 .append("Relationship").append(",")
        //                 .append("Relationship").append(",")
        //                 .append("2023").append(",")
        //                 .append("5")
        //                 .append("\n");
        //     }
        //     relationshipFileWriter.write(builder.toString());
        //     builder.delete(0, builder.capacity());
        // }

        nodeFileWriter.close();
        // relationshipFileWriter.close();
    }
}

package org.dml.mockData;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileWriter;

/**
 * @author : yangzhuo
 */
@SpringBootTest
public class mockNodeWithRelation {
    private static final int NODE_COUNT = 2000_0000;

    @Test
    public void mockDataNeo4jAdminImportTest() throws Exception {
        String nodePath = "D:\\neo4j\\neo4j-community-3.5.35\\import\\node.csv";
        String relationshipPath = "D:\\neo4j\\neo4j-community-3.5.35\\import\\relationship.csv";
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
}

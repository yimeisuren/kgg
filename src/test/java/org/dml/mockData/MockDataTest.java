package org.dml.mockData;

import org.dml.constance.CustomRedisKey;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.*;

/**
 * @author : yangzhuo
 */
@SpringBootTest
public class MockDataTest {

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    /**
     * 生成neo4j测试数据文件
     * @throws IOException 路径错误
     */
    @Test
    void mockDataForTwentyMillion() throws IOException {
        // CALL apoc.import.csv([{fileName: 'file:/node-apoc-i.csv', labels: ['Node']}], [], {})
        for(int i = 0; i < 10 ; ++i) {
            String path = "D:\\Graph2_programs\\neo4j-community-3.5.11\\import\\node-apoc-" + i + ".csv";
            File file = new File(path);
            FileWriter fileWriter = new FileWriter(file, false);
            fileWriter.write("id:ID,:LABEL,attributes.name:STRING,attributes.time:STRING,attributes.winner:STRING\n");
            StringBuilder builder = new StringBuilder();
            for (int j = i * 1000000; j <(i+1) * 1000000; j++) {
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

    /**
     * 通过 mockDataForTwentyMillion() 生成的csv文件，生成redis的批量导入txt文件
     * @throws IOException 路径错误
     */
    @Test
    public void creatNodeImportFile() throws IOException {
        //输出的管道文本文件路径
        String outPath = "D:\\Graph2_programs\\pipeline.txt";
        File outFile = new File(outPath);
        FileWriter fileWriter = new FileWriter(outFile, false);
        for(int i=0; i< 10; ++i) {
            String inPath = "D:\\Graph2_programs\\neo4j-community-3.5.11\\import\\node-apoc-" + i + ".csv";
            File inFile = new File(inPath);
            try {
                //从字符输入流读取文本，缓冲各个字符，从而实现字符、数组和行（文本的行数通过回车符来进行判定）的高效读取。
                BufferedReader textFile = new BufferedReader(new FileReader(inFile));
                String lineData;
                StringBuilder builder = new StringBuilder();

                //先把csv文件头读取掉
                textFile.readLine();
                //将文档的下一行数据赋值给lineData，并判断是否为空，若不为空则输出
                while ((lineData = textFile.readLine()) != null) {
                    String[] tmp = lineData.split(",");
                    String id = tmp[0];
                    String[] labels = tmp[1].split(";");

                    //先写入id对应的set
                    String s = builder.append("sadd ").append(CustomRedisKey.NODE)
                            .append(" ").append(id).append("\r\n")
                            .toString();
                    fileWriter.write(s);
                    builder.delete(0, builder.capacity());

                    //再写入label对应id
                    for (String label : labels) {
                        s = builder.append("sadd ").append(label)
                                .append(" ").append(id).append("\r\n")
                                .toString();
                        fileWriter.write(s);
                        builder.delete(0, builder.capacity());
                    }
                }
                textFile.close();
            } catch (FileNotFoundException e) {
                System.out.println("没有找到指定文件");
            } catch (IOException e) {
                System.out.println("文件读写出错");
            }
        }
        fileWriter.close();
    }

    /**
     * 使用redis库实现批量导入
     * 使用 mockDataForTwentyMillion() 生成的csv文件
     * 每二十万条数据执行一次管道命令，防止内存负载过高，在 CustomRedisKey 配置文件中修改
     */
    @Test
    public void redisImportFromCsv(){
        redisTemplate.executePipelined((RedisConnection connection) -> {
            for(int i=0; i< 1; ++i) {
                String inPath = "D:\\Graph2_programs\\neo4j-community-3.5.11\\import\\node-apoc-" + i + ".csv";
                File inFile = new File(inPath);
                try {
                    //从字符输入流读取文本，缓冲各个字符，从而实现字符、数组和行（文本的行数通过回车符来进行判定）的高效读取。
                    BufferedReader textFile = new BufferedReader(new FileReader(inFile));
                    String lineData;

                    //先把csv文件头读取掉
                    textFile.readLine();
                    //将文档的下一行数据赋值给lineData，并判断是否为空，若不为空则输出
                    while ((lineData = textFile.readLine()) != null) {
                        String[] tmp = lineData.split(",");
                        byte[] id = tmp[0].getBytes();
                        byte[] node = CustomRedisKey.NODE.getBytes();
                        String[] labels = tmp[1].split(";");

                        //先写入id对应的set
                        connection.sAdd(node, id);

                        //再写入label对应id
                        for (String label : labels) {
                            connection.sAdd(label.getBytes(), id);
                        }
                    }
                    System.out.println("完成了文件: " + i);
                } catch (FileNotFoundException e) {
                    System.out.println("没有找到指定文件");
                } catch (IOException e) {
                    System.out.println("文件读写出错");
                }
            }

            return null;
        });
    }

}

// package org.dml;
//
// import org.dml.entities.NodeX;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.data.neo4j.core.Neo4jOperations;
// import org.springframework.data.neo4j.core.Neo4jTemplate;
// import org.springframework.data.neo4j.repository.query.QueryFragmentsAndParameters;
//
// import java.util.Collections;
// import java.util.List;
// import java.util.Map;
//
// @SpringBootTest
// public class Neo4jTemplateTest {
//     @Autowired
//     private Neo4jTemplate neo4jTemplate;
//
//     @Test
//     public void saveTest() {
//         NodeX teacher1 = new NodeX("t1");
//         teacher1.addLabel("TeacherX");
//         teacher1.addAttribute("name", "老师甲");
//         teacher1.addAttribute("age", "30");
//         neo4jTemplate.save(teacher1);
//
//
//         NodeX teacher2 = new NodeX("t2");
//         teacher2.addLabel("TeacherX");
//         teacher2.addAttribute("name", "老师已");
//         teacher2.addAttribute("age", "31");
//         neo4jTemplate.save(teacher2);
//     }
//
//     @Test
//     public void neo4jTemplateTest() {
//         String name = "老师甲";
//         String cypher = "MATCH (p:TeacherX{`attributes.name`:$name}) RETURN p";
//
//         Map<String, Object> parameters = Collections.singletonMap("name", name);
//         QueryFragmentsAndParameters queryFragmentsAndParameters = new QueryFragmentsAndParameters(cypher, parameters);
//
//         Neo4jOperations.ExecutableQuery<NodeX> relationshipExecutableQuery = neo4jTemplate.toExecutableQuery(NodeX.class, queryFragmentsAndParameters);
//         List<NodeX> results = relationshipExecutableQuery.getResults();
//         results.forEach(System.out::println);
//
//         // 返回结果是单个值的情况
//         // Optional<Relationship> singleResult = relationshipExecutableQuery.getSingleResult();
//         // Relationship relationship = singleResult.get();
//         // System.out.println("relationship = " + relationship);
//     }
// }

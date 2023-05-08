// package org.dml.dao;
//
// import org.dml.entities.NodeX;
// import org.dml.entities.RelationshipX;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.data.neo4j.core.Neo4jTemplate;
//
// import java.util.*;
//
// @SpringBootTest
// public class RelationshipXRepositoryTest {
//
//     @Autowired
//     private NodeXRepository nodeXRepository;
//
//     @Autowired
//     private RelationshipXRepository relationshipXRepository;
//
//     @Test
//     public void saveRelationshipWithSaveNodeTest() {
//         NodeX from = nodeXRepository.findById(2L).get();
//         // NodeX from = nodeXRepository.findById(20006553L).get();
//         System.out.println("from = " + from);
//
//         // TODO: 如果to是new Node()生成的, 那么一切正常. 如果to也是通过findById查找出来的节点呢?
//         NodeX to = new NodeX();
//         to.addAttribute("name", "学生A");
//         NodeX resutlTo = nodeXRepository.save(to);
//         System.out.println("resutlTo = " + resutlTo);
//
//         // 为relationship设置目标节点
//         // 此时to节点必须在数据库中已经存在, 否则报错
//         String relationshipType = "Relationship";
//         RelationshipX relationship = new RelationshipX(relationshipType, to);
//
//         Map<String, Set<RelationshipX>> relationships = from.getRelationships();
//         Set<RelationshipX> typeRelationship = relationships.getOrDefault(relationshipType, new HashSet<>());
//         typeRelationship.add(relationship);
//         relationships.put(relationshipType, typeRelationship);
//
//
//         NodeX result = nodeXRepository.save(from);
//         System.out.println("result = " + result);
//     }
//
//
//     @Test
//     public void saveWithFindByIdTest() {
//         // TODO: 第1条MATCH: 查询自己节点
//         // MATCH (nodeX:`Node`)
//         // WHERE id(nodeX) = $__id__
//         // WITH collect(id(nodeX)) AS __sn__
//         // RETURN __sn__
//
//         // TODO: 后面的MATCH, 根据有几条出边对应几次MATCH查询; 例如id=7的有5条出边, 因此后面会再查询5次
//         // MATCH (nodeX:`Node`) WHERE id(nodeX) = $__id__
//         // OPTIONAL MATCH (nodeX)-[__sr__]->(__srn__:`Node`)
//         // WITH collect(id(nodeX)) AS __sn__,
//         //      collect(id(__srn__)) AS __srn__,
//         //      collect(id(__sr__)) AS __sr__
//         // RETURN __sn__, __srn__, __sr__
//
//         // MATCH (nodeX:`Node`) WHERE id(nodeX) IN $__ids__ OPTIONAL MATCH (nodeX)-[__sr__]->(__srn__:`Node`) WITH collect(id(nodeX)) AS __sn__, collect(id(__srn__)) AS __srn__, collect(id(__sr__)) AS __sr__ RETURN __sn__, __srn__, __sr__
//
//         // MATCH (rootNodeIds) WHERE id(rootNodeIds) IN $rootNodeIds WITH collect(rootNodeIds) AS n OPTIONAL MATCH ()-[relationshipIds]-() WHERE id(relationshipIds) IN $relationshipIds WITH n, collect(DISTINCT relationshipIds) AS __sr__ OPTIONAL MATCH (relatedNodeIds) WHERE id(relatedNodeIds) IN $relatedNodeIds WITH n, __sr__ AS __sr__, collect(DISTINCT relatedNodeIds) AS __srn__ UNWIND n AS rootNodeIds WITH rootNodeIds AS nodeX, __sr__, __srn__ RETURN nodeX AS __sn__, __sr__, __srn__
//
//
//         NodeX from = nodeXRepository.findById(20006554L).get();
//         System.out.println("from = " + from);
//
//         // TODO: 如果to是new Node()生成的, 那么一切正常. 如果to也是通过findById查找出来的节点呢?
//         //  根本问题在于:
//         //  findById不能查找通过neo4j-admin import方式导入的含有出边的节点, to节点不能有出边. 否则报错: org.springframework.data.mapping.MappingException: Error mapping Record
//         //  而通过程序添加的节点, 即使有出边也可以使用findById()查询到正确的结果
//         // NodeX to = nodeXRepository.findById(1L).get();
//         NodeX to = nodeXRepository.findById(7L).get();
//
//         String relationshipType = "Relationship";
//         RelationshipX relationship = new RelationshipX(relationshipType, to);
//
//         Map<String, Set<RelationshipX>> relationships = from.getRelationships();
//         Set<RelationshipX> typeRelationship = relationships.getOrDefault(relationshipType, new HashSet<>());
//         typeRelationship.add(relationship);
//         relationships.put(relationshipType, typeRelationship);
//
//
//         NodeX result = nodeXRepository.save(from);
//         System.out.println("result = " + result);
//     }
//
//
//     //  而通过程序添加的节点, 即使有出边也可以使用findById()查询到正确的结果
//     @Test
//     public void saveWithFindById2Test() {
//         NodeX from = nodeXRepository.findById(1L).get();
//
//
//         NodeX to = nodeXRepository.findById(20006554L).get();
//
//         String relationshipType = "Relationship";
//         RelationshipX relationship = new RelationshipX(relationshipType, to);
//
//         Map<String, Set<RelationshipX>> relationships = from.getRelationships();
//         Set<RelationshipX> typeRelationship = relationships.getOrDefault(relationshipType, new HashSet<>());
//         typeRelationship.add(relationship);
//         relationships.put(relationshipType, typeRelationship);
//
//
//         NodeX result = nodeXRepository.save(from);
//         System.out.println("result = " + result);
//     }
//
//     @Test
//     public void findSaveNodeByIdTest() {
//         // 无法将relationships映射成节点中的属性
//         // TODO: id=20006554是通过程序的逻辑插入的, 能够成功通过findById映射出来
//         NodeX node = nodeXRepository.findById(20006554L).get();
//         System.out.println("node = " + node);
//
//         // TODO:id=1 和 id=7的Node都是批量导入的,
//         //  其中id=1没有出边, 可以查找到.
//         //  但是id=7有出边, 不能够通过findById映射, 下面的会报错.
//         //  二者同Cypher语句直接在Neo4j中查询得到的节点并没有本质上的区别
//
//         // org.springframework.data.mapping.MappingException: Error mapping Record
//         // <{
//         // __sn__: node<7>,
//         // __sr__: [relationship<100002240>, relationship<5>, relationship<6>, relationship<7>, relationship<8>, relationship<9>, relationship<10>, relationship<11>, relationship<12>, relationship<13>, relationship<14>],
//         // __srn__: [node<1>, node<2>, node<3>, node<4>, node<5>, node<6>, node<15>]
//         // }>
//         NodeX node1 = nodeXRepository.findById(1L).get();
//         System.out.println("node1 = " + node1);
//
//         NodeX node7 = nodeXRepository.findById(7L).get();
//         System.out.println("node = " + node7);
//
//
//     }
//
//     @Test
//     public void saveTest() {
//         NodeX from = nodeXRepository.findById(5L).get();
//         NodeX toA = nodeXRepository.findById(16L).get();
//
//         HashMap<String, Set<RelationshipX>> relationships = new HashMap<>();
//
//         RelationshipX r1 = new RelationshipX("Relationship", toA);
//         r1.addAttribute("行为1", "无");
//         r1.addAttribute("行为2", "null");
//
//
//         Set<RelationshipX> relationshipXES = new HashSet<>();
//         relationshipXES.add(r1);
//
//         // RelationshipX r2 = new RelationshipX("rrrr2", "争执", "nnnn1", "nnnn2", toA);
//         // r2.addAttribute("争执事件", "课堂吵架");
//         // r2.addAttribute("发生时间", "2000-11-11");
//
//         relationships.put(r1.getType(), relationshipXES);
//         // relationships.put(r2.getLabel(), r2);
//
//         from.setRelationships(relationships);
//
//         nodeXRepository.save(from);
//
//         // NodeX toB = new NodeX("n3");
//         // toB.addLabel("student");
//         // toB.addAttribute("name", "学生B");
//         // toB.addAttribute("birthdate", "2000-11-11");
//         //
//         //
//
//         //
//         // RelationshipX r2 = new RelationshipX("rr2", "争执", from, toB);
//         // r2.addAttribute("争执事件", "课堂吵架");
//         // r2.addAttribute("发生时间", "2000-11-11");
//         //
//         //
//         // HashMap<String, RelationshipX> relationships = new HashMap<>();
//         // relationships.put(r1.getLabel(), r1);
//         // relationships.put(r2.getLabel(), r2);
//         // from.setRelationships(relationships);
//         //
//         // nodeXRepository.save(toA);
//         // nodeXRepository.save(toB);
//         // nodeXRepository.save(from);
//     }
//
//     @Autowired
//     private Neo4jTemplate neo4jTemplate;
//
//     // @Test
//     // public void findByIdWithNeo4jTemplateTest() {
//     //     Optional<RelationshipEntityX> result = neo4jTemplate.findById(169L, RelationshipEntityX.class);
//     //     RelationshipEntityX relationshipX = result.get();
//     //     System.out.println("relationshipX = " + relationshipX);
//     // }
//
//     @Test
//     public void findByIdTest() {
//         RelationshipX relationshipX = relationshipXRepository.findById(167L).get();
//         System.out.println("relationshipX = " + relationshipX);
//     }
//
//     @Test
//     public void deleteByIdTest() {
//         relationshipXRepository.deleteById(166L);
//     }
// }

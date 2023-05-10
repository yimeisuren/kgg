package org.dml.dao.impl;

import org.dml.dao.CustomizedNodeRepository;
import org.dml.entities.Node;
import org.neo4j.driver.internal.InternalNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

/**
 * @author : yangzhuo
 */
@Service
public class CustomizedNodeRepositoryImpl implements CustomizedNodeRepository {
    @Autowired
    private Neo4jClient neo4jClient;

    @Override
    public Optional<Node> customizedFindById(Long id) {
        String cypher = "MATCH (n:Node) WHERE id(n)= $id RETURN n";

        return neo4jClient.query(cypher)
                .bind(id).to("id")
                .fetchAs(Node.class)
                .mappedBy(
                        (typeSystem, record) -> {
                            //这里的"n"是上面的cypher语句中的RETURN n
                            InternalNode node = (InternalNode) record.get("n").asNode();
                            Node result = new Node();
                            result.setId(node.id());
                            for (String label : node.labels()) {
                                result.addLabel(label);
                            }
                            // 获取节点属性值
                            Map<String, Object> properties = node.asMap();

                            result.setAttributes(properties);

                            return result;
                        })
                .one();
    }

    @Override
    public Long customizedCountByLabel(String label) {
        String cypher = "MATCH (n:LABEL) return count(*) as c";
        cypher = cypher.replace("LABEL", label);
        Map<String, Object> result = neo4jClient.query(cypher)
                .fetch()
                .one().get();
        // 如果数据库中没有对应的label数据，返回值为0，不会为空
        return (Long) result.get("c");
    }

}

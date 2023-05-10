package org.dml.dao.impl;

import org.dml.dao.CustomizedRelationshipRepository;
import org.dml.entities.Relationship;
import org.neo4j.driver.Record;
import org.neo4j.driver.internal.InternalRelationship;
import org.neo4j.driver.types.TypeSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

/**
 * @author : yangzhuo
 */
@Component
public class CustomizedRelationshipRepositoryImpl implements CustomizedRelationshipRepository {
    @Autowired
    private Neo4jClient neo4jClient;

    @Override
    public Optional<Relationship> customizedFindById(Long id) {
        String cypher = "MATCH (p)-[r]->() WHERE id(r)= $id RETURN r, p";
        return neo4jClient.query(cypher)
                .bind(id).to("id")
                .fetchAs(Relationship.class)
                .mappedBy(
                        (TypeSystem typeSystem, Record record) -> {
                            //这里的"r"是上面的cypher语句中的RETURN r
                            InternalRelationship relationship = (InternalRelationship) record.get("r").asRelationship();

                            Relationship result = new Relationship();
                            result.setType(relationship.type());
                            result.setId(relationship.id());
                            result.setFromId(relationship.startNodeId());
                            result.setToId(relationship.endNodeId());
                            // 获取节点属性值
                            Map<String, Object> properties = relationship.asMap();

                            result.setAttributes(properties);

                            return result;
                        })
                .one();
    }

    @Override
    public void customizedDeleteById(Long id) {
        String cypher = "MATCH ()-[r]->() WHERE id(r)= $id DELETE r";
        neo4jClient.query(cypher)
                .bind(id).to("id")
                .run();
    }

    @Override
    public void customizedDeleteByType(String type) {
        String cypher = "MATCH ()-[r:TYPE]->()  DELETE r";
        cypher = cypher.replace("TYPE", type);
        neo4jClient.query(cypher).run();
    }

    @Override
    public Long customizedCountByType(String type) {
        String cypher = "MATCH ()-[r:TYPE]->() return count(r) as c";
        cypher = cypher.replace("TYPE", type);
        Map<String, Object> result = neo4jClient.query(cypher)
                .fetch()
                .one().get();
        // 如果数据库中没有对应的type数据，返回值为0，不会为空
        return (Long) result.get("c");
    }
}

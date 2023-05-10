package org.dml.converter;

import org.dml.entities.Relationship;
import org.neo4j.driver.internal.value.RelationshipValue;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author : yangzhuo
 */
@Component
public class RelationShipTypeConverter implements Converter<org.neo4j.driver.internal.value.RelationshipValue, org.dml.entities.Relationship> {

    @Override
    public Relationship convert(RelationshipValue source) {
        System.out.println("convertToRelationShipType" + source);
        Long id = source.get("id").asLong();
        String type = source.get("type").asString();
        Long startNodeId = source.get("startNodeId").asLong();
        Long endNodeId = source.get("endNodeId").asLong();
        Map<String, Object> properties = source.asMap();
        Relationship relationship = new Relationship();
        properties.remove("id");
        properties.remove("type");
        properties.remove("startNodeId");
        properties.remove("endNodeId");
        relationship.setFromId(startNodeId);
        relationship.setToId(endNodeId);
        relationship.setType(type);
        relationship.setId(id);
        //relationship.setAttributes(properties);
        return relationship;
    }
}

//public class RelationShipTypeConverter implements GenericConverter {
//
//    @Override
//    public Set<ConvertiblePair> getConvertibleTypes() {
//        Set<ConvertiblePair> convertiblePairs = new HashSet<>();
//        convertiblePairs.add(new ConvertiblePair(org.dml.entities.Relationship.class, org.neo4j.driver.internal.value.RelationshipValue.class));
//        convertiblePairs.add(new ConvertiblePair(org.neo4j.driver.internal.value.RelationshipValue.class, org.dml.entities.Relationship.class));
//        return convertiblePairs;
//    }
//
//    @Override
//    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
//        if (Relationship.class.isAssignableFrom(sourceType.getType())) {
//            // convert to Neo4j Driver Value
//            return convertToNeo4jValue(source);
//        } else {
//            // convert to MyCustomType
//            return convertToRelationShipType(source);
//        }
//    }
//
//    private Object convertToRelationShipType(Object source) {
//        System.out.println("convertToRelationShipType" + source);
//        RelationshipValue relValue = (RelationshipValue) source;
//        org.neo4j.driver.types.Relationship rel = relValue.asRelationship();
//        Relationship relationship = new Relationship();
//        relationship.setFromId(rel.startNodeId());
//        relationship.setToId(rel.endNodeId());
//        relationship.setType(rel.type());
//        relationship.setId(rel.id());
//
//        return relationship;
//
//    }
//
//    private Object convertToNeo4jValue(Object source){
//        System.out.println("convertToNeo4jValue" + source);
//        // Create a start node
//        org.neo4j.driver.types.Node startNode = (Node) Values.parameters("name", "Alice");
//
//// Create an end node
//        org.neo4j.driver.types.Node endNode = (Node) Values.parameters("name", "Bob");
//        org.neo4j.driver.types.Relationship relationship = Values.value(
//                Values.parameters(
//                        "id", 123,
//                        "startNodeId", 1,
//                        "endNodeId", 2,
//                        "type", "KNOWS"
//                )).asRelationship();
//        return new RelationshipValue(relationship);
//    }
//
//
//}

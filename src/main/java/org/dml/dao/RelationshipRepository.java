package org.dml.dao;

import org.dml.entities.Relationship;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelationshipRepository extends Neo4jRepository<Relationship, Long> , CustomizedRelationshipRepository{

}

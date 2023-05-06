package org.dml.dao;

import org.dml.entities.Relationship;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface RelationshipRepository extends Neo4jRepository<Relationship, String> {

    @Override
    Relationship save(Relationship relationship);
}

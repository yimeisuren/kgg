package org.dml.dao;

import org.dml.entities.RelationshipX;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RelationshipXRepository extends Neo4jRepository<RelationshipX, Long> {



    @Override
    @Query("MATCH p=()-[r]->() WHERE id(r)=164 RETURN p")
    Optional<RelationshipX> findById(@Param("id") Long id);





    @Override
    @Query("MATCH ()-[r]->() WHERE id(r) = $id delete r")
    void deleteById(@Param("id") Long id);
}

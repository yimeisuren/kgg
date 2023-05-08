package org.dml.dao;

import org.dml.entities.NodeX;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NodeXRepository extends Neo4jRepository<NodeX, Long> {


    /**
     * 从元数据中查找值应该使用 labels() 来进行查询
     *
     * @param label
     * @return
     */
    @Query("MATCH (n) WHERE $label IN labels(n) RETURN n")
    List<NodeX> findNodesByLabelsContains(String label);


    // @Query("CALL apoc.")

    // void run(String query);

    @Query("MATCH (n) DETACH DELETE n")
    void clearDB();


    // /**
    //  * 移除一个实体的标签, 如果这个实体只剩下这一个标签, 则直接删除该实体
    //  *
    //  * @param label
    //  * @param remain
    //  * @return
    //  */
    // @Query("MATCH (n) REMOVE n:$label")
    // boolean removeLabel(String label);


    // @Query("MATCH (n) WHERE id(n)=$id RETURN n")
    // @Override
    // Optional<NodeX> findById(Long id);
}

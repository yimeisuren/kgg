package org.dml.dao;

import org.dml.entities.Node;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NodeRepository extends Neo4jRepository<Node, String> {


    /**
     * 从元数据中查找值应该使用 labels() 来进行查询
     *
     * @param label
     * @return
     */
    @Query("MATCH (n) WHERE $label IN labels(n) RETURN n")
    List<Node> findNodesByLabelsContains(String label);



    // /**
    //  * 移除一个实体的标签, 如果这个实体只剩下这一个标签, 则直接删除该实体
    //  *
    //  * @param label
    //  * @param remain
    //  * @return
    //  */
    // @Query("MATCH (n) REMOVE n:$label")
    // boolean removeLabel(String label);


}

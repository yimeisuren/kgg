package org.dml.service;

import org.dml.entities.Relationship;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface RelationshipService {
    /**
     * 添加relation
     *
     * @param fromId 边的头节点id
     * @param toId 边的尾节点id
     * @param type 边的type
     * @param attributes 边的属性
     */
    void addRelationship(Long fromId, Long toId, String type, Map<String, Object> attributes);

    void addRelationship(Long fromId, Long toId, String type);
    /**
     * 通过id来删除边
     *
     * @param id 待删除边的id
     * @return
     */
    int deleteRelationshipById(Long id);

    int deleteRelationshipByIds(Long... ids);

    int deleteRelationshipByIds(Collection<Long> ids);

    /**
     * 通过label来删除边
     *
     * @param type
     * @return
     */
    int deleteRelationshipByType(String type);

    /**
     * 通过id查找边
     * @param id 查找边的id
     * @return 存在返回对应的边, 不存在则返回null
     */
    Relationship findRelationshipById(Long id);


    List<Relationship> findRelationshipsByType(String type);
}
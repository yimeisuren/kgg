package org.dml.service;

public interface RelationshipService {
    /**
     * 添加relation
     *
     * @param fromId
     * @param toId
     * @param id
     * @param label
     */
    void addRelationship(String fromId, String toId, String id, String label);

    /**
     * 通过id来删除边
     *
     * @param id
     * @return
     */
    int deleteRelationshipById(String id);

    /**
     * 通过label来删除边
     *
     * @param label
     * @return
     */
    int deleteRelationshipByLabel(String label);
}

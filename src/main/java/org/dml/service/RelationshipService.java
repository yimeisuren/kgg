package org.dml.service;

import org.dml.entities.Relationship;

import java.util.Collection;
import java.util.List;

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

    int deleteRelationshipByIds(String... ids);

    int deleteRelationshipByIds(Collection<String> ids);

    /**
     * 通过label来删除边
     *
     * @param label
     * @return
     */
    int deleteRelationshipByLabel(String label);


    Relationship findRelationshipById(String id);


    List<Relationship> findRelationshipsByLabel(String label);
}

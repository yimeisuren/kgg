package org.dml.dao;

import org.dml.entities.Relationship;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author : yangzhuo
 */
@Repository
public interface CustomizedRelationshipRepository {

    /**
     * 根据id查询节点，并未自动绑定关系
     * @param id node的id
     * @return optional<node>
     */
    Optional<Relationship> customizedFindById(Long id);

    void customizedDeleteById(Long id);

    void customizedDeleteByType(String type);

    Long customizedCountByType(String type);
}

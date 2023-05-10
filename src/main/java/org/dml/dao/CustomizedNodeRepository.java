package org.dml.dao;

import org.dml.entities.Node;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author : yangzhuo
 */
@Repository
public interface CustomizedNodeRepository {

    Optional<Node> customizedFindById(Long id);

    Long customizedCountByLabel(String label);

}

package org.dml.repository.impl;

import org.dml.dao.NodeRepository;
import org.dml.service.NodeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author : yangzhuo
 */
@SpringBootTest
public class CustomizedNodeRepositoryImplTest {
    @Autowired
    NodeRepository nodeRepository;

    @Autowired
    NodeService nodeService;

    @Test
    void findById(){
        nodeRepository.customizedFindById(22L).ifPresent(System.out::println);
    }

    @Test
    void countNodeByLabel(){
        System.out.println(nodeRepository.customizedCountByLabel("Node"));
    }

}

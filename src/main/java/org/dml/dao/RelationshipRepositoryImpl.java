package org.dml.dao;

import org.dml.entities.Relationship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.neo4j.core.Neo4jOperations.*;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.data.neo4j.repository.query.QueryFragmentsAndParameters;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;


//TODO: 存在的问题,
// 1. 希望能够继承Neo4jRepository, 只用重写其中几个方法,
// 这些方法通过@Query注解不方便取到参数值, 所以通过注入neo4jTemplate来进行操作.
// 但是这样就被迫使用实现类, 而使用实现类就需要重写Neo4jRepository中的所有方法, 如何解决这样一个两难问题?
// 类比Jpa中找到RelationshipRepository的默认实现类, 但是那个类以final进行修饰, 无法被继承, 这又是一个两难问题? 可能只能重写所有的方法
@Repository
public class RelationshipRepositoryImpl implements RelationshipRepository {

    @Autowired
    private Neo4jTemplate neo4jTemplate;


    @Override
    public Relationship save(Relationship relationship) {
        return null;
    }

    @Override
    public <S extends Relationship> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Relationship> findById(String id) {
        // TODO: 下面是chatgpt生成的一段代码, 参考
        String name = "";
        String cypher = "MATCH (p:Person) WHERE p.name = {name} RETURN p";
        Map<String, Object> parameters = Collections.singletonMap("name", name);
        QueryFragmentsAndParameters queryFragmentsAndParameters = new QueryFragmentsAndParameters(cypher, parameters);
        ExecutableQuery<Relationship> relationshipExecutableQuery = neo4jTemplate.toExecutableQuery(Relationship.class, queryFragmentsAndParameters);
        return relationshipExecutableQuery.getSingleResult();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public List<Relationship> findAll() {
        return null;
    }

    @Override
    public List<Relationship> findAllById(Iterable<String> iterable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public void delete(Relationship entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends Relationship> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Relationship> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Relationship> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Relationship> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Relationship> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Relationship> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Relationship> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Relationship> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Relationship> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Relationship, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}

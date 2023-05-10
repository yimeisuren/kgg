package org.dml.service;

import org.dml.entities.Node;

import java.util.Collection;
import java.util.List;

public interface NodeService {
    /**
     * 增加实体节点
     *
     * @param node 待插入的节点
     * @return 成功插入的节点数量
     */
    int addNode(Node node);

    /**
     * 批量增加实体节点
     *
     * @param nodes 待插入的批量节点
     * @return 实际成功插入的节点数量
     */
    int addNodes(Collection<Node> nodes);

    /**
     * 通过 id 来删除实体节点
     *
     * @param id 待删除节点的id
     * @return 成功删除节点的数量
     */
    int deleteNodeById(Long id);


    /**
     * 通过 ids 来批量删除节点
     *
     * @param ids 待删除节点的id
     * @return 成功删除节点的数量
     */
    int deleteNodesByIds(Collection<Long> ids);

    /**
     * 更新节点
     * <p>
     * 目前节点的更新, 会使得原有节点的属性被保留, 只会进行同名属性的覆盖, 不是整个node进行替换.
     * 如果需要这样的功能, 需要额外实现删除属性的方法
     *
     * @param node
     * @return
     */
    int updateNode(Node node);

    /**
     * 批量更新节点
     *
     * @param nodes
     * @return
     */
    int updateNodes(Collection<Node> nodes);

    /**
     * 通过单个id查询单个Node实体
     * <p>
     * 查找方法中最核心的实现, 其它所有方法都是基于此进行实现的, 有的还会利用redis中的映射来辅助实现
     *
     * @param id 节点的id
     * @return 存在返回对应的节点, 不存在则返回null
     */
    Node findNodeById(Long id);


    /**
     * 通过一个id列表查询多个Node实体
     * <p>
     * 用途: 简化代码
     *
     * @param ids
     * @return
     */
    List<Node> findNodesByIds(Collection<Long> ids);

    /**
     * 通过 label 来获取实体
     * <p>
     * 一个 label 可能会返回多个实体
     *
     * @param label 标签
     * @return 该标签下的所有实体
     */
    List<Node> findNodesByLabel(String label);

    /**
     * 返回通过满足多个标签的实体
     * <p>
     * 子图匹配的时候可能需要使用
     *
     * @param labels 需要满足的标签的集合
     * @return 满足所有标签的实体
     */
    List<Node> findNodesByLabels(Collection<String> labels);

    /**
     * 根据标签删除节点, 在Redis中保存neo4j中label到id的映射, 先在Redis中找到label对应的id的集合, 然后再根据id来进行删除
     *
     * @param label
     * @return
     */
    int deleteNodesByLabel(String label);

    /**
     * 删除同时具有所有labels的节点
     *
     * @param labels
     * @return
     */
    int deleteNodesByLabels(Collection<String> labels);

    /**
     * TODO: 清空数据库数据, 方便进行测试, 实际使用时需要将其放到工具类中可能会更好
     */
    void clearDB();
}
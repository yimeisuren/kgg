package org.dml.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.neo4j.core.schema.CompositeProperty;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 因为是自定义类型关系, 底层将关系建立为一个节点类型进行存储
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@RelationshipProperties
public class RelationshipX implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 关系的唯一标识符
     * <p>
     * 一个数据库中只能有一个不相同的id
     */
    @Id
    @NonNull
    private String id;

    /**
     * 关系的标签
     * <p>
     * 一个关系只能由一个label
     */
    @Property
    @NonNull
    private String label;


    /**
     * TODO: 这里保存节点还是保存节点的id呢?
     * <p>
     * 保存节点在图数据库中可以看到节点和节点之间的关系, 虽然也不明显就是.
     * <p>
     * 保存节点id的话, 相当于图数据库只是作为了一个简单的key-value键值对的存储, 后面的子图匹配是否需要使用到这种图存储引擎本身管理的复杂的拓扑结构信息
     */
    @org.springframework.data.neo4j.core.schema.Relationship(type = "from", direction = org.springframework.data.neo4j.core.schema.Relationship.Direction.OUTGOING)
    @NonNull
    private Node from;

    @org.springframework.data.neo4j.core.schema.Relationship(type = "to", direction = org.springframework.data.neo4j.core.schema.Relationship.Direction.OUTGOING)
    @NonNull
    private Node to;


    /**
     * 关系的属性集合
     */
    @CompositeProperty
    private Map<String, String> attributes = new HashMap<>();

    /**
     * 为关系添加属性
     *
     * @param key   属性key
     * @param value 属性value
     */
    public void addAttribute(String key, String value) {
        this.attributes.put(key, value);
    }

    /**
     * 为关系删除某个属性
     *
     * @param key 属性key
     */
    public void deleteAttribute(String key) {
        this.attributes.remove(key);
    }

    /**
     * 获取关系的某个属性值
     *
     * @param key 属性key
     * @return 属性value
     */
    public String getAttribute(String key) {
        return this.attributes.get(key);
    }

    @Override
    public String toString() {
        return "Relationship{" +
                "id='" + id + '\'' +
                ", label='" + label + '\'' +
                ", fromNodeId=" + from.getId() +
                ", toNodeId=" + to.getId() +
                ", attributes=" + attributes +
                '}';
    }
}

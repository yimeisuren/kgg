package org.dml.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.neo4j.core.schema.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 因为是自定义类型关系, 底层将关系建立为一个节点类型进行存储
 */
@Data
@NoArgsConstructor
@RelationshipProperties
public class RelationshipX implements Serializable {

    private static final long serialVersionUID = 1L;

    //TODO: 这里的id是个问题.
    // 两个方面的问题:
    // 1. 是否可以不使用@GeneratedValue? 使用@GeneratedValue后会出现重复
    // 2. 使用String类型的id? 不是什么大事情, 只是Redis中是String类型, 而Neo4j中又保存Long类型, 写代码的时候每次都要转换一下
    @RelationshipId
    private Long id;

    public RelationshipX(@NonNull String type, @NonNull NodeX to) {
        this.type = type;
        this.to = to;
        this.toId = to.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RelationshipX that = (RelationshipX) o;
        return type.equals(that.type) && fromId.equals(that.fromId) && toId.equals(that.toId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, fromId, toId);
    }

    @Override
    public String toString() {
        return "RelationshipX{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", fromId=" + fromId +
                ", toId=" + toId +
                ", attributes=" + attributes +
                '}';
    }

    /**
     * 关系的标签
     * <p>
     * 一个关系只能由一个label
     */
    @Property
    @NonNull
    private String type;

    @Property
    private Long fromId;

    @Property
    private Long toId;


    /**
     * TODO: 这里保存节点还是保存节点的id呢?
     * <p>
     * 保存节点在图数据库中可以看到节点和节点之间的关系, 虽然也不明显就是.
     * <p>
     * 保存节点id的话, 相当于图数据库只是作为了一个简单的key-value键值对的存储, 后面的子图匹配是否需要使用到这种图存储引擎本身管理的复杂的拓扑结构信息
     */
    // @StartNode
    // @NonNull
    // private NodeX from;
    //
    @TargetNode
    @NonNull
    private NodeX to;

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

}

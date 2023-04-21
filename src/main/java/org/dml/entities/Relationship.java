package org.dml.entities;

import lombok.*;
import org.springframework.data.neo4j.core.schema.CompositeProperty;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Property;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 因为是自定义类型关系, 底层将关系建立为一个节点类型进行存储
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@org.springframework.data.neo4j.core.schema.Node
public class Relationship implements Serializable {
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

}

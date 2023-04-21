package org.dml.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.neo4j.core.schema.CompositeProperty;
import org.springframework.data.neo4j.core.schema.DynamicLabels;
import org.springframework.data.neo4j.core.schema.Id;

import java.io.Serializable;
import java.util.*;


/**
 * 所有的实体类都应该直接使用该类, 通过label来进行区分
 */
@NoArgsConstructor
@RequiredArgsConstructor
@Data
@org.springframework.data.neo4j.core.schema.Node
public class Node implements Serializable {
    /**
     * 网络传输：序列化
     */
    private static final long serialVersionUID = 1L;


    /**
     * 实体唯一标识
     */
    @Id
    @NonNull
    private String id;


    /**
     * Node实体的标签, 允许拥有多个标签
     */
    @DynamicLabels
    private List<String> labels = new ArrayList<>();

    /**
     * 实体属性集
     */
    @CompositeProperty
    private Map<String, String> attributes = new HashMap<>();

    /**
     * TODO: 所有的出边
     * <p>
     * key: 出边的标签label
     * <p>
     * List: 该标签下对应的关系的id集合
     */
    @CompositeProperty
    private Map<String, Set<String>> outs = new HashMap<>();


    /**
     * 为实体添加属性
     *
     * @param key   待添加属性的key
     * @param value 待添加属性的value
     */
    public void addAttribute(String key, String value) {
        this.attributes.put(key, value);
    }

    /**
     * 删除实体中的某个属性
     *
     * @param key 待删除属性的key
     */
    public void deleteAttribute(String key) {
        this.attributes.remove(key);
    }

    /**
     * 获取实体属性集合中某个key对应的value
     *
     * @param key 属性key
     * @return 属性value
     */
    public String getAttribute(String key) {
        return this.attributes.get(key);
    }

    /**
     * 为实体添加标签
     *
     * @param label
     */
    public void addLabel(String label) {
        this.getLabels().add(label);
    }

    /**
     * 为实体添加多个标签
     *
     * @param labels
     */
    public void addLabels(String... labels) {
        for (String label : labels) {
            addLabel(label);
        }
    }

    public void addOutRelationship(String relationLabel, String relationId) {
        Set<String> labelToIds = this.outs.getOrDefault(relationLabel, new HashSet<>());
        labelToIds.add(relationId);
        outs.putIfAbsent(relationLabel, labelToIds);
    }


    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

    /**
     * 判断两个Node对象是否相同
     *
     * @param obj
     * @return id相同返回true, id不同或不是同类对象,则返回false
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Node) {
            Node node = (Node) obj;
            return this.id.equals(node.id);
        }
        return false;
    }

}

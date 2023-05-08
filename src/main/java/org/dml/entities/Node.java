package org.dml.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.neo4j.driver.internal.value.StringValue;
import org.springframework.data.neo4j.core.schema.CompositeProperty;
import org.springframework.data.neo4j.core.schema.DynamicLabels;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.io.Serializable;
import java.util.*;


/**
 * 所有的实体类都应该直接使用该类, 通过label来进行区分
 */
@NoArgsConstructor
@RequiredArgsConstructor
@Data
@org.springframework.data.neo4j.core.schema.Node(value = "null")
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
     * key: 出边的标签label
     * <p>
     * List: 该标签下对应的关系的id集合
     */
    @CompositeProperty
    // TODO: @CompositeProperty注解中的转换器处理的是Map<String, Object>类型,
    //  虽然可以得到Set, 但是泛型被擦除, 所以后面获取时表面上得到的是Set<String>, 但实际得到的是Set<StringValue>类型. 为一系列问题埋下隐患
    private Map<String, Set<String>> outs = new HashMap<>();


    @org.springframework.data.neo4j.core.schema.Relationship(direction = Relationship.Direction.OUTGOING)
    private Map<String, List<Relationship>> relationships = new HashMap<>();

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

    public void addOutRelationship(String label, String id) {
        Set<String> labelToIds = this.outs.getOrDefault(label, new HashSet<>());
        labelToIds.add(id);
        outs.putIfAbsent(label, labelToIds);
    }

    public void deleteOutRelationship(String label, String id) {
        Set<String> labelToIds = this.outs.getOrDefault(label, null);
        if (labelToIds != null) {
            // 由于没有实现对Map<String, Set<String>>类型的自定义转换器,
            // 因此这里得到的labelToIds实际上是Set<StringValue>类型(泛型擦除), 因此需要将id包装成StringValue类型才能够被正确删除
            labelToIds.remove(new StringValue(id));
            this.outs.put(label, labelToIds);
        }

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

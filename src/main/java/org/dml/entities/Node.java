package org.dml.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.schema.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 所有的实体类都应该直接使用该类, 通过label来进行区分
 */
@NoArgsConstructor
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
    @GeneratedValue
    private Long id;

    /**
     * Node实体的标签, 允许拥有多个标签
     */
    @DynamicLabels
    private List<String> labels = new ArrayList<>();

    /**
     * 实体属性集
     */
    @CompositeProperty
    private Map<String, Object> attributes = new HashMap<>();


    // TODO: dynamic relationships不能指定type
    @org.springframework.data.neo4j.core.schema.Relationship(direction = Relationship.Direction.OUTGOING)
    private Map<String, List<org.dml.entities.Relationship>> relationships = new HashMap<>();


    /**
     * 为节点添加一个type为label，id为id的出边
     *
     * @param relationship 添加的边
     */
    public void addRelationship(org.dml.entities.Relationship relationship) {
        relationship.setFromId(this.id);

        String relationshipType = relationship.getType();
        List<org.dml.entities.Relationship> typeRelationships = relationships.getOrDefault(relationshipType, new ArrayList<>());

        boolean addFlag = true;
        for (int i = 0; i < typeRelationships.size(); i++) {
            if (typeRelationships.get(i).equals(relationship)) {
                // 如果待插入的边相等, 那么替换掉原来的边, 不用插入
                typeRelationships.set(i, relationship);
                // 替换取代添加, addFlag设置为false
                addFlag = false;
                break;
            }
        }

        if (addFlag) {
            typeRelationships.add(relationship);
        }
        relationships.put(relationshipType, typeRelationships);
    }

    /**
     * 从节点删除一个type为label，id为id的出边
     *
     * @param relationship 删除的边
     */
    public void deleteRelationship(org.dml.entities.Relationship relationship) {
        relationship.setFromId(this.id);

        String relationshipType = relationship.getType();
        List<org.dml.entities.Relationship> typeRelationships = relationships.getOrDefault(relationshipType, new ArrayList<>());

        for (int i = 0; i < typeRelationships.size(); i++) {
            if (typeRelationships.get(i).equals(relationship)) {
                typeRelationships.remove(i);
                break;
            }
        }
        relationships.put(relationshipType, typeRelationships);
    }

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
        return (String) this.attributes.get(key);
    }

    /**
     * 为实体添加标签
     *
     * @param label 添加的标签
     */
    public void addLabel(String label) {
        this.getLabels().add(label);
    }

    /**
     * 为实体添加多个标签
     *
     * @param labels 添加的标签列表
     */
    public void addLabels(String... labels) {
        for (String label : labels) {
            addLabel(label);
        }
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

    /**
     * 判断两个Node对象是否相同
     *
     * @param obj 待比较的node对象
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

    @Override
    public String toString() {

        return "Node:{" +
                "id=" + id +
                ", labels=" + labels +
                ", attributes=" + attributes +
                ", relationships= ..." +
                '}';
    }

}
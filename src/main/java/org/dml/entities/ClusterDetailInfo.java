package org.dml.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 集群详细信息
 * <p>
 * todo: 如果是集群的详细信息，那么为什么是主机的ip地址，感觉这个类定义的莫名其妙
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClusterDetailInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主机ip地址
     */
    private String host;

    /**
     * 集群中的实体数量
     */
    private Long entityNum;

    /**
     * 集群中的关系数量
     */
    private Long relationNum;

    /**
     * 集群中实体的标签集合
     */
    private List<String> entityLabels;

    /**
     * 集群中关系的标签集合
     */
    private List<String> relationLabels;

    /**
     * 集群中的索引信息集合
     */
    private List<DbIndexInfo> indexInfos;
}

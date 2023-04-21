package org.dml.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 单机图谱存储索引信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DbIndexInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String description;

    private String state;

    private String type;
}

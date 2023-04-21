// package org.dml.entities;
//
// import lombok.AllArgsConstructor;
// import lombok.Data;
// import lombok.NoArgsConstructor;
//
// import java.io.Serializable;
// import java.util.*;
//
// @Data
// @NoArgsConstructor
// @AllArgsConstructor
// public class Graph implements Serializable {
//     private static final long serialVersionUID = 1L;
//
//     /**
//      * 图中所有的实体
//      * <p>
//      * 按照出度大小进行逆序排序
//      */
//     private Set<Node> vertices = new HashSet<>();
//
//     // 图中所有的关系
//     private Set<Relationship> edges = new HashSet<>();
//
//
//     /**
//      * 添加顶点
//      *
//      * @param vertex
//      */
//     public void addNode(Node vertex) {
//         vertices.add(vertex);
//     }
//
//     /**
//      * 删除顶点
//      * <p>
//      * 删除顶点的同时需要删除顶点关联的边
//      *
//      * @param vertex
//      */
//     public void deleteNode(Node vertex) {
//
//     }
//
//     /**
//      * 添加边
//      *
//      * @param edge
//      */
//     public void addEdge(Relationship edge) {
//         // 插入边
//         edges.add(edge);
//
//         //插入边带有的节点
//         Node from = edge.getFrom();
//         Node to = edge.getTo();
//         vertices.add(from);
//         vertices.add(to);
//
//         from.addNeighbour(edge);
//     }
//
//
// }

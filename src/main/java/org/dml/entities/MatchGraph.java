// package org.dml.entities;
//
// import lombok.Data;
//
// import java.io.Serializable;
// import java.util.ArrayList;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;
//
// @Data
// public class MatchGraph implements Serializable {
//
//     private static final long serialVersionUID = 1L;
//
//     /**
//      * 查询图节点queryId与数据图实体标识的映射
//      * <p>
//      * 所谓的匹配图，关键信息也只是这张映射表
//      */
//     private Map<Integer, Node> gidToEntityMap = new HashMap<>();
//
//     /**
//      * 匹配图的所有实体
//      */
//     private List<Node> entities;
//
//     /**
//      * 匹配图的所有关系集合
//      */
//     private List<Relationship> edges;
//
//
//     /**
//      * 添加映射关系
//      *
//      * @param queryId 查询节点查询编号
//      * @param vertex  匹配实体
//      */
//     public void addMapping(int queryId, Node vertex) {
//         this.gidToEntityMap.put(queryId, vertex);
//     }
//
//     /**
//      * 两张匹配图合并前先进行检查
//      * <p>
//      * 遍历较小的那张匹配图，提高一些性能
//      *
//      * @param other 另一张匹配图
//      * @return 两张图是否满足合并要求
//      */
//     private boolean check(MatchGraph other) {
//         Map<Integer, Node> thisMap = this.gidToEntityMap;
//         Map<Integer, Node> otherMap = other.gidToEntityMap;
//
//         // thisMap更大，遍历otherMap
//         if (thisMap.size() >= otherMap.size()) {
//             for (Integer key : otherMap.keySet()) {
//                 if (!otherMap.get(key).equals(thisMap.getOrDefault(key, null))) {
//                     return false;
//                 }
//             }
//             return true;
//         }
//
//         // otherMap更大，遍历thisMap
//         for (Integer key : thisMap.keySet()) {
//             if (!thisMap.get(key).equals(otherMap.getOrDefault(key, null))) {
//                 return false;
//             }
//         }
//         return true;
//     }
//
//     /**
//      * 两个（中间）匹配图尝试合并
//      * <p>
//      * 如果两张匹配图没有冲突，就可以合并；否则就返回null
//      *
//      * @param other 输入的另一个中间匹配结果
//      * @return 若能够合并则返回合并结果，否则返回null
//      */
//     public MatchGraph merge(MatchGraph other) {
//         if (!check(other)) {
//             return null;
//         }
//
//         // 可以合并
//         MatchGraph mergeGraph = new MatchGraph();
//
//         Map<Integer, Node> thisMap = this.getGidToEntityMap();
//         Map<Integer, Node> otherMap = other.getGidToEntityMap();
//         Map<Integer, Node> map = new HashMap<>();
//         map.putAll(thisMap);
//         map.putAll(otherMap);
//         mergeGraph.setGidToEntityMap(map);
//
//         List<Node> thisEntities = this.getEntities();
//         List<Node> otherEntities = other.getEntities();
//         List<Node> vertexMerge = new ArrayList<>();
//         vertexMerge.addAll(thisEntities);
//         vertexMerge.addAll(otherEntities);
//         mergeGraph.setEntities(vertexMerge);
//
//         List<Relationship> thisEdges = this.getEdges();
//         List<Relationship> otherEdges = other.getEdges();
//         List<Relationship> edgeMerge = new ArrayList<>();
//         edgeMerge.addAll(thisEdges);
//         edgeMerge.addAll(otherEdges);
//         mergeGraph.setEdges(edgeMerge);
//
//         return mergeGraph;
//     }
//
//     /**
//      * 深拷贝函数
//      *
//      * @return
//      */
//     public MatchGraph deepCopy() {
//         MatchGraph copy = new MatchGraph();
//         copy.getGidToEntityMap().putAll(gidToEntityMap);
//         return copy;
//     }
//
//     @Override
//     public String toString() {
//         StringBuilder builder = new StringBuilder();
//         builder.append("MatchGraph : [").append("\n");
//         gidToEntityMap.forEach((key, value) -> {
//             builder.append("\t")
//                     .append("<")
//                     .append(key)
//                     .append(" : ")
//                     .append(value)
//                     .append(">")
//                     .append("\n");
//         });
//         builder.append("]").append("\n");
//         return builder.toString();
//     }
//
// }
//

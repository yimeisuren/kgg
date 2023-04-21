// package org.dml.entities;
//
// import java.util.*;
//
// public class QueryGraph extends Graph {
//     private static final int TOP_K = 5;
//     private static final int DEPTH = 5;
//
//     /**
//      * 将一张图通过层序遍历简化成多张星形图
//      * <p>
//      * todo：可以设置一下最大层数为5
//      *
//      * @return
//      */
//     public List<Graph> simplify() {
//         // 按结点的度进行倒序排序
//         List<Node> vertices = new ArrayList<>(getVertices());
//         vertices.sort((node1, node2) -> -(node1.getNeighbours().size() - node2.getNeighbours().size()));
//
//         List<Graph> result = new ArrayList<>();
//         if (vertices.size() == 0) {
//             return result;
//         }
//
//         // 前topK个优先遍历, 希望能够尽早发现不匹配的图，而出度越大越有可能在数据图中找不到
//         int topK = Math.min(vertices.size(), TOP_K);
//         for (int i = 0; i < topK; i++) {
//             result.add(new StarGraph(vertices.get(i)));
//         }
//
//         // 从top1节点开始进行BFS，限制BFS的最大层数
//         int maxDepth = Math.min(vertices.size(), DEPTH);
//         Set<Node> visited = new HashSet<>();
//         Queue<Node> queue = new LinkedList<>();
//         Node topVertex = vertices.get(0);
//         queue.offer(topVertex);
//         while (maxDepth-- > 0 && !queue.isEmpty()) {
//             int size = queue.size();
//             while (size-- > 0) {
//                 // 访问当前结点
//                 Node vertex = queue.poll();
//                 // 标记当前访问节点，防止重复访问
//                 visited.add(vertex);
//                 result.add(new StarGraph(vertex));
//                 // 获取节点的邻居
//                 for (Relationship edge : vertex.getNeighbours()) {
//                     Node neighbor = edge.getTo();
//                     if (!visited.contains(neighbor)) {
//                         queue.add(neighbor);
//                     }
//                 }
//             }
//         }
//
//         return result;
//     }
// }

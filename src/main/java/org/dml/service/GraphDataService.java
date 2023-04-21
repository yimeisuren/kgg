// package org.dml.service;
//
// import org.dml.entities.*;
//
// import java.time.LocalDateTime;
// import java.util.List;
// import java.util.Map;
// import java.util.Set;
//
// /**
//  * 图操作接口
//  */
// public interface GraphDataService {
//
//     /**
//      * 获取本机存储的实体数
//      * <p>
//      * (包含核心实体和扩展实体)
//      *
//      * @return 本机上的实体数
//      */
//     public Long getEntityNum();
//
//     /**
//      * 获取本机存储的关系数
//      * <p>
//      * (包含核心关系和扩展关系)
//      *
//      * @return 本机上的关系数
//      */
//     public Long getRelationNum();
//
//     /**
//      * 获取本机上所有的实体标签
//      *
//      * @return 本机上所有实体标签
//      */
//     public List<String> getEntityLabels();
//
//     /**
//      * 获取本机上所有的关系标签
//      *
//      * @return 本机上所有的关系标签
//      */
//     public List<String> getRelationLabels();
//
//     /**
//      * 获取本机上节点所有的属性键值
//      *
//      * @return 本机上所有的属性键
//      */
//     public List<String> getKeys();
//
//     /**
//      * 获取本机上所有的索引信息
//      *
//      * @return 本机上所有的索引信息
//      */
//     public List<DbIndexInfo> getDbIndexInfos();
//
//     /**
//      * 获取本机图谱存储详细信息
//      *
//      * @return
//      */
//     public ClusterDetailInfo getClusterDetailInfo();
//
//     /**
//      * 判断当前服务所在机器的图数据库中是否包含特定实体
//      *
//      * @param entityId 实体唯一标识
//      * @return 存在返回true，不存在返回false
//      */
//     public boolean containEntity(String entityId);
//
//     /**
//      * 将实体添加到当前服务所在机器的图数据中
//      *
//      * @param vertex 实体
//      * @return 成功返回true，否则返回false
//      */
//     public boolean addEntity(Node vertex);
//
//     /**
//      * 更新当前服务所在机器的图数据中的实体信息（实体的唯一标识不能够修改）
//      *
//      * @param srcVertex 待更新的实体
//      * @param dstVertex 包含更新信息实体
//      * @return 成功返回true，否则返回false
//      */
//     public boolean updateEntity(final Node srcVertex, final Node dstVertex);
//
//     /**
//      * 删除当前服务所在机器的图数据中的实体
//      *
//      * @param entityId 实体唯一标识
//      * @return 成功返回true，否则返回false
//      */
//     public boolean deleteEntity(String entityId);
//
//     /**
//      * 根据实体在当前服务所在机器的图数据中的图元素编号查找实体
//      *
//      * @param graphId 实体在图数据库中的图元素编号
//      * @return 满足条件的实体
//      */
//     public Node findEntityByGraphId(final long graphId);
//
//     /**
//      * 根据实体的唯一标识在当前服务所在机器的图数据中查找实体
//      *
//      * @param entityId 实体唯一标识
//      * @return 满足条件的实体
//      */
//     public Node findEntityByEntityId(final String entityId);
//
//     /**
//      * 根据实体的标签集合在当前服务所在机器的图数据中查找实体
//      *
//      * @param labels 实体标签列表
//      * @return 满足条件的实体列表
//      */
//     public List<Node> findEntityByLabels(final List<String> labels);
//
//     /**
//      * 根据实体的属性集合在当前服务所在机器的图数据中查找实体
//      *
//      * @param attributes 实体属性集合
//      * @return 满足条件的实体集合
//      */
//     public List<Node> findEntityByAttributes(final Map<String, String> attributes);
//
//     /**
//      * 根据实体的标签集合与实体的属性集合在当前服务所在机器的图数据中查找实体
//      *
//      * @param labels     实体标签集合
//      * @param attributes 实体属性集合
//      * @return 满足条件的实体
//      */
//     public List<Node> findEntityByLabelsAndAttributes(final List<String> labels, final Map<String, String> attributes);
//
//     /**
//      * 判断当前服务所在机器的图数据中是否存在特定的关系
//      *
//      * @param relationId 关系的唯一标识
//      * @return 成功返回true，否则返回false
//      */
//     public boolean containRelationship(final String relationId);
//
//     /**
//      * 将关系添加到当前服务所在机器的图数据中
//      *
//      * @param crtRela 关系
//      * @return 成功返回true，否则返回false
//      */
//     public boolean addRelationship(final Relationship crtRela);
//
//     /**
//      * 更新当前服务所在机器的图数据中的关系（关系的唯一标识不可以修改）
//      *
//      * @param srcRelaId 带更新的实体
//      * @param dstRela   包含更新信息的实体
//      * @return 成功返回true，否则false
//      */
//     public boolean updateRelationship(final String srcRelaId, final Relationship dstRela);
//
//     /**
//      * 删除当前服务所在机器的图数据中特定的关系（不会删除关系相关联的实体）
//      *
//      * @param relaId 关系唯一标识
//      * @return 成功返回true，否则返回false
//      */
//     public boolean deleteRelationship(final String relaId);
//
//     /**
//      * 根据关系的唯一标识在当前服务所在机器的图数据中查找关系
//      *
//      * @param relaId 关系唯一标识
//      * @return 满足特定条件的关系
//      */
//     public Relationship findRelaByRelaId(final String relaId);
//
//     /**
//      * 根据关系的（单一）标签在当前服务所在机器的图数据中查找关系
//      *
//      * @param label 关系的标签（类型）
//      * @return 满足特定条件的关系集合
//      */
//     public List<Relationship> findRelaByLabel(final String label);
//
//     /**
//      * 根据关系的属性在当前服务所在机器的图数据中查找关系
//      *
//      * @param attributes 关系的属性集合
//      * @return 满足特定条件的关系集合
//      */
//     public List<Relationship> findRelaByAttributes(final Map<String, String> attributes);
//
//     /**
//      * 根据关系的（单一）标签和属性集合在当前服务所在机器的图数据中查找关系
//      *
//      * @param label      关系标签（类型）
//      * @param attributes 关系属性集合
//      * @return 满足特定条件的关系集合
//      */
//     public List<Relationship> findyRelaByLabelAndAttibutes(final String label, final Map<String, String> attributes);
//
//     /**
//      * 在当前服务所在机器的图数据中查找以特定实体为起点的关系
//      *
//      * @param startVertex 关系的起点实体
//      * @return 满足特定条件的关系集合
//      */
//     public List<Relationship> findRelaByStartEntity(final Node startVertex);
//
//     /**
//      * 在当前服务所在机器的图数据中查找以特定实体为终点的关系，即入边表
//      *
//      * @param vertex 关系的终点实体
//      * @return 满足条件的关系集合
//      */
//     public List<Relationship> findRelaByEndEntity(final Node vertex);
//
//     /**
//      * 在当前服务所在机器的图数据中根据关系的起点和终点查询关系
//      *
//      * @param from 关系的起点实体
//      * @param to   关系的终点实体
//      * @return 满足条件的关系集合
//      */
//     public List<Relationship> findRelationshipByEntity(final Node from, final Node to);
//
//     /**
//      * 在本地单机上进行查询图的星形子查询图匹配
//      *
//      * @param starGraph 查询图分解后的星形查询图
//      * @return 星形查询图的匹配结果
//      */
//     public List<MatchGraph> starGraphMatch(StarGraph starGraph);
//
//     /**
//      * 根据identity从redis中获取Entity实体
//      *
//      * @param ID identity
//      * @return
//      */
//     public Node findentitybyredis(String ID);
//
//     public void reCache(String nodename, String recache);
//
//     /**
//      * @param ID 漫游点的ID
//      * @return Map<String, Object>中含新增点和新增边的集合，其获取点和边集合请用：
//      * <p>
//      * List<Node> newNodes = (List<Node>)Map.get("nodes");
//      * List<Relationship> newEdges = (List<Relationship>)Map.get("edges");
//      */
//     Map<String, Object> tripDataWithRedis(String ID, Set<String> exists);
//
//     public String searchCacheFile(String nodename);
//
//
//     /**
//      * 将/home/dml-kg/cachetest/tips路径下的某一文件（Files索引0-9）重新命名为nodename,若文件数小于10直接创建文件。
//      *
//      * @param nodename 文件名（也可能是文件夹名？）
//      */
//     public void reTips(String nodename);
//
//     /**
//      * 获取/home/dml-kg/cachetest/tips路径下的文件
//      *
//      * @return json字符串，[{"value":fileName},...,{"value":fileName}]
//      */
//     public String searchTips();
//
//     public String getAverageTime(double onetime);
//
//     public List<Relationship> findjoneRelaById(String nid);
//
//     public Long CaseTypeNum();
//
//     public Long ConceptionTypeNum();
//
//     public Long EntityTypeNum();
//
//     public List<Relationship> findjoneRelaByRedis(String nid);
//
//
//     void findRedis(String ID);
//
//
//     /**
//      * 时空查询接口
//      */
//     public String quickQuery(LocalDateTime start, LocalDateTime end, double longitude1, double latitude1, double longitude2, double latitude2);
//
//
//     public void testRmi(String str);
//
//     public void outputPath(LocalDateTime start, LocalDateTime end, double longitude1, double latitude1, double longitude2, double latitude2);
//
// }
//

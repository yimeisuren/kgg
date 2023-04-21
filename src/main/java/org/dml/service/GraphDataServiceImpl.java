// package org.dml.service;
//
// import org.dml.dao.NodeRepository;
// import org.dml.dao.RelationshipRepository;
// import org.dml.entities.*;
// import org.neo4j.driver.Driver;
// import org.neo4j.driver.Session;
// import org.neo4j.driver.Transaction;
// import org.neo4j.driver.TransactionWork;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.configurationprocessor.json.JSONArray;
// import org.springframework.boot.configurationprocessor.json.JSONObject;
// import org.springframework.data.redis.core.RedisTemplate;
// import org.springframework.stereotype.Component;
//
// import javax.annotation.Resource;
// import java.io.*;
// import java.text.DecimalFormat;
// import java.time.LocalDate;
// import java.time.LocalDateTime;
// import java.time.ZoneOffset;
// import java.util.*;
//
// /**
//  * 图操作实现类
//  *
//  * @author HuangBo
//  *
//  */
// @Component
// public class GraphDataServiceImpl implements GraphDataService {
//
// 	private final Logger logger = LoggerFactory.getLogger(GraphDataServiceImpl.class);
//
// 	@Autowired
// 	private final Driver driver;
//
// 	@Autowired
// 	private final NodeRepository entityRepository;
//
// 	@Autowired
// 	private final RelationshipRepository relationshipRepository;
//
// 	@Resource(name = "labelInexRedisTemplate")
// 	private RedisTemplate<String, String> labelInexRedisTemplate;
//
// 	@Resource(name = "nodeDataRedisTemplate")
// 	private RedisTemplate<String, NodeDataInfo> nodeDataRedisTemplate;
//
// 	@Resource(name = "template1")
// 	private RedisTemplate<String, String> template1;
//
// //	private final String cachfileDir = "/home/dml-kg/cachetest/cache";
// //	private final String tipFileDir = "/home/dml-kg/cachetest/tips";
// 	private final String cachfileDir = "C:/cachetest/cache";
// 	private final String tipFileDir = "C:/cachetest/tips";
//
// 	File f = new File(cachfileDir);
// 	int filecount = 0;
//
// 	String tipsCache = "";
// 	File tipsfile = new File(tipFileDir);
// 	int tipcount =0;
//
// 	double[] averagetime =  new double[10];
// 	int timecount =0;
//
// 	int flag = 0;
//
// 	public GraphDataServiceImpl(Driver driver, NodeRepository entityRepository, RelationshipRepository relationshipRepository) {
// 		this.driver = driver;
// 		this.entityRepository = entityRepository;
// 		this.relationshipRepository = relationshipRepository;
// 	}
//
// 	@Override
// 	public String getAverageTime(double onetime) {
// 		double avertime = 0;
// 		String outtime = "";
// 		int count = 0;
// 		if(timecount<10) {
// 			averagetime[timecount] = onetime;
// 			timecount++;
// 			for(int i=0;i<timecount;i++) {
// 				avertime = avertime +averagetime[i];
// 		}
// 		DecimalFormat df = new DecimalFormat("0.000");
// 		avertime = avertime /timecount;
// 		outtime = df.format(avertime);
// 		return outtime;
// 		}
// 		else if(timecount>=10){
// 			count = timecount%10;
// 			timecount ++;
// 			averagetime[count] = onetime;
// 			for(int j =0;j<10;j++) {
// 				avertime = avertime + averagetime[j];
// 			}
// 			avertime = avertime /10.000;
// 			outtime = String.valueOf(avertime);
// 		}
// 		return outtime;
// 	}
//
// 	@Override
// 	public String searchTips() {
// 		File[] tipsfiles1 = tipsfile.listFiles();
// 		tipsCache = "[";
// 		for(int j = 0 ; j < tipsfiles1.length;j++) {
// 			tipsCache = tipsCache+"{\"value\":\""+tipsfiles1[j].getName()+"\"},";
// 		}
// 		tipsCache = tipsCache.substring(0,tipsCache.length()-1);
// 		tipsCache = tipsCache +"]";
// //		System.out.println("返回提示："+tipsCache);
// 		return tipsCache;
// 	}
//
// 	@Override
// 	public void reTips(String nodename) {
// 		System.out.println("here!");
// 		File[] tipsfiles = tipsfile.listFiles();
// 		flag = 0;
// 		for(File f1 :tipsfiles) {
// 			if(f1.getName().equals(nodename)==true) {
// 				flag = 1;
// 				break;
// 			}
// 		}
// 		if (flag == 0) {
// 			String roottipPath = tipsfiles[tipcount].getParent();
// 			//String roottipPath =tipFileDir;
// 			File newtipFile = new File(roottipPath + File.separator + nodename);
// 			if(tipsfiles.length>=10) {
// 				if (tipsfiles[tipcount].renameTo(newtipFile))
// 				  {
// 					System.out.println("修改成功");
// 				  }
// 				  else
// 				  {
// 				   System.out.println("修改失败");
// 				  }
// 				System.out.println("当前覆盖文件编号："+tipcount);
// 				tipcount++;
// 				tipcount = tipcount%10;
//
// 			}
// 			else {
// 					try {
// 						newtipFile.createNewFile();
// 						System.out.println("addtip succuss");
// 					} catch (IOException e) {
// 						// TODO Auto-generated catch block
// 						e.printStackTrace();
// 					}
// 			}
//
// 		}
// 	}
//
// 	@Override
// 	public List<Relationship> findjoneRelaById(String nid) {
// 		return relationshipRepository.findjone(nid);
// 	}
//
//
// 	@Override
// 	public List<Relationship> findjoneRelaByRedis(String ID) {
// 		System.out.println("server findjoneRelaByRedis start--");
// 		if(ID==null)
// 			return null;
// 		if(nodeDataRedisTemplate.hasKey(ID) == false)
// 		{
// 			return null;
// 		}
// 		NodeDataInfo myNode = nodeDataRedisTemplate.opsForValue().get(ID);
// 		System.out.print((myNode.getNodeId()));
// 		System.out.print(ID);
// 		List<NodeNeigh> neighbors = myNode.getNeighbors();
// 		List<Relationship> res = new ArrayList<>();
// 		for(int i = 0;i != neighbors.size();++i)
// 		{
// 			NodeNeigh temp = neighbors.get(i);
// 			res.add(new Relationship("",temp.getEdgeLabel(),null,ID,temp.getNeighId()));
// 		}
// 		return res;
// 	}
//
// 	@Override
// 	public Map<String,Object> tripDataWithRedis(String ID,Set<String> exixts){
// 		//System.out.println("server tripDataWithRedis start --- ");
// 		System.out.println("ID: " + ID);
// 		if(ID==null){
// 			System.out.println("id is null");
// 			return null;
// 		}
//
// 		if(nodeDataRedisTemplate.hasKey(ID) == false)
// 		{
// 			System.out.println("donot exist id");
// 			return null;
// 		}
//
// 		System.out.println("continue ..");
//
// 		NodeDataInfo myNode = nodeDataRedisTemplate.opsForValue().get(ID);
// 		List<NodeNeigh> neighbors = myNode.getNeighbors();
//
// 		Map<String,Object> resMap = new HashMap<>();
//
// 		Set<Relationship> newEdge = new HashSet<>();
// 		Set<Node> newNode = new HashSet<>();
//
//
// 		if(neighbors != null){
// 			for (NodeNeigh temp:neighbors){
// 				//temp为一跳节点
// 				if (!exixts.contains(temp.getNeighId())){
// 					newNode.add(new Node(temp.getNeighId(),temp.getNeighLabels(),null));
// 					newEdge.add(new Relationship("",temp.getEdgeLabel(),null,ID,temp.getNeighId()));
// 				}
// 				//通过两跳节点找新增点到已有点集的边
// 				NodeDataInfo twoJump = nodeDataRedisTemplate.opsForValue().get(temp.getNeighId());
// 				System.out.println(twoJump);
// 				if(twoJump == null){
// 					System.out.println("twoJump is null ...");
// 					continue;
// 				}
//
// 				List<NodeNeigh>	neighbors2 = twoJump.getNeighbors();
// 				for (NodeNeigh temp2:neighbors2){
// 					if (exixts.contains(temp2.getNeighId()))
// 						newEdge.add(new Relationship("",temp2.getEdgeLabel(),null,temp.getNeighId(),temp2.getNeighId()));
// 				}
// 			}
// 		}
//
// 		resMap.put("nodes",newNode);
// 		resMap.put("edges",newEdge);
//
// 		System.out.println(resMap.toString());
//
// 		return resMap;
//
// 	}
//
// 	@Override
// 	public String searchCacheFile(String nodename) {
// 		File[] filesname = f.listFiles();
// 		String GraphData = "";
// 		for(File f:filesname) {
// 			if(f.getName().equals(nodename)==true) {
// 				 try {
// 		                    InputStreamReader read = new InputStreamReader(new FileInputStream(f));
// 		                    BufferedReader bufferedReader = new BufferedReader(read);
// 		                    GraphData = bufferedReader.readLine();
// 		                    read.close();
// 		        } catch (Exception e) {
// 		            System.out.println("读取文件内容出错");
// 		            e.printStackTrace();
// 		        }
// //				byte[] bbuf = new byte[102400];
// //				FileInputStream fin;
// //				try {
// //					fin = new FileInputStream(f);
// //					int l=0;
// //		            while ((l=fin.read(bbuf))!=-1) {
// //		            	GraphData = new String(bbuf, 0, l);
// //		                System.out.println(GraphData);
// //			}
// //		         fin.close();
// //				} catch (FileNotFoundException e) {
// //					// TODO Auto-generated catch block
// //					e.printStackTrace();
// //				} catch (IOException e) {
// //					// TODO Auto-generated catch block
// //					e.printStackTrace();
// //				}
// 				return GraphData;
// 		}
// 	}
//
// 		return GraphData;
// 	}
//
// 	@Override
// 	public void reCache(String nodename,String recache) {
// 		File[] filesname = f.listFiles();
// 		flag = 0;
// 		for(File f2:filesname) {
// 			if(f2.getName().equals(nodename)==true) {
// 				flag = 1;
// 				break;
// 			}
// 		}
// 		if(flag == 0) {
// 		String rootPath = filesname[filecount].getParent();
// 		File newFile = new File(rootPath + File.separator + nodename);
// 		BufferedWriter writer = null;
// 		if(filesname.length>=2000) {
// 			  try {
// 		            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filesname[filecount],false), "UTF-8"));
// 		            writer.write(recache);
// 		        } catch (IOException e) {
// 		            e.printStackTrace();
// 		        }finally {
// 		            try {
// 		                if(writer != null){
// 		                    writer.close();
// 		                }
// 		            } catch (IOException e) {
// 		                e.printStackTrace();
// 		            }
// 		        }
// 			if (filesname[filecount].renameTo(newFile))
// 			  {
// 				System.out.println("修改成功");
// 			  }
// 			  else
// 			  {
// 			   System.out.println("修改失败");
// 			  }
// 			filecount++;
// 			System.out.println("当前覆盖文件编号："+filecount);
// 		}
// 		else {
// 			  try {
// 		            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(newFile,false), "UTF-8"));
// 		            writer.write(recache);
// 					System.out.println("修改成功");
// 		        } catch (IOException e) {
// 		            e.printStackTrace();
// 		        }finally {
// 		            try {
// 		                if(writer != null){
// 		                    writer.close();
// 		                }
// 		            } catch (IOException e) {
// 		                e.printStackTrace();
// 		            }
// 		        }
// 		}
// 		}
// 	}
//
// 	public Long EntityTypeNum() {
// 		try {
// 			try (Session session = driver.session()) {
// 				long typeNum = session.readTransaction(new TransactionWork<Long>() {
// 					@Override
// 					public Long execute(Transaction tx) {
// 						String cypher = "MATCH (n{datatype:'1'}) RETURN　count(n) AS Num1";
// 						Long typeNum = tx.run(cypher).single().get("Num1").asLong();
// //						System.out.println("typenum1:"+typeNum);
// 						return typeNum;
// 					}
// 				});
// 				return typeNum;
// 			}
// 		} catch (Exception e) {
// 			System.err.println("get type number failed for:" + e.getMessage());
// 			return null;
// 		}
// 	}
//
// 	public Long ConceptionTypeNum() {
// 		try {
// 			try (Session session = driver.session()) {
// 				long typeNum = session.readTransaction(new TransactionWork<Long>() {
// 					@Override
// 					public Long execute(Transaction tx) {
// 						String cypher = "MATCH (n{datatype:'2'}) RETURN　count(n) AS Num2";
// 						Long typeNum = tx.run(cypher).single().get("Num2").asLong();
// //						System.out.println("typenum2:"+typeNum);
// 						return typeNum;
// 					}
// 				});
// 				return typeNum;
// 			}
// 		} catch (Exception e) {
// 			System.err.println("get type number failed for:" + e.getMessage());
// 			return null;
// 		}
// 	}
//
// 	public Long CaseTypeNum() {
// 		try {
// 			try (Session session = driver.session()) {
// 				long typeNum = session.readTransaction(new TransactionWork<Long>() {
// 					@Override
// 					public Long execute(Transaction tx) {
// 						String cypher = "MATCH (n{datatype:'3'}) RETURN　count(n) AS Num3";
// 						Long typeNum = tx.run(cypher).single().get("Num3").asLong();
// //						System.out.println("typenum3:"+typeNum);
// 						return typeNum;
// 					}
// 				});
// 				return typeNum;
// 			}
// 		} catch (Exception e) {
// 			System.err.println("get type number failed for:" + e.getMessage());
// 			return null;
// 		}
// 	}
//
//
//
// 	public Long EntityNum() {
// 		System.out.println("EntityNum start --");
//
// 		try {
// 			try (Session session = driver.session()) {
// 				long entityNum = session.readTransaction(new TransactionWork<Long>() {
// 					@Override
// 					public Long execute(Transaction tx) {
// 						String cypher = "MATCH (n) RETURN count( n) AS entityNum";
// 						Long entityNum = tx.run(cypher).single().get("entityNum").asLong();
// 						return entityNum;
// 					}
// 				});
// 				System.out.println("EntityNum: " + entityNum);
// 				return entityNum;
// 			}
// 		} catch (Exception e) {
// 			logger.error("get entity number failed for:" + e.getMessage());
// 			return new Long(0);
// 		}
// 	}
//
//
// 	public long relaNum() {
// 		System.out.println("relaNum start--");
// 		try {
// 			try (Session session = driver.session()) {
// 				long relaNum = session.readTransaction(new TransactionWork<Long>() {
// 					@Override
// 					public Long execute(Transaction tx) {
// 						String cypher = "MATCH ()-[r]->() RETURN count( r) AS relaNum";
// 						Long relaNum = tx.run(cypher).single().get("relaNum").asLong();
// 						return relaNum;
// 					}
// 				});
// 				System.out.println("relaNum: " + relaNum);
// 				return relaNum;
// 			}
// 		} catch (Exception e) {
// 			logger.error("get relationship number failed for:" + e.getMessage());
// 			return 0;
// 		}
// 	}
//
// 	public List<String> dbLabels() {
// 		try {
// 			try (Session session = driver.session()) {
// 				List<String> labels = session.readTransaction(new TransactionWork<List<String>>() {
// 					@Override
// 					public List<String> execute(Transaction tx) {
// 						//update by ws start
// 						String cypher = "CALL db.labels() YIELD label";
// 						//String cypher = "CALL db.labels() YIELD label RETURN label limit 10";
// 						//update by hn end
// 						List<String> labels = new ArrayList<>();
// 						StatementResult result = tx.run(cypher);
// 						while (result.hasNext()) {
// 							String label = result.next().get("label").asString();
// 							labels.add(label);
// 						}
// 						return labels;
// 					}
// 				});
// 				return labels;
// 			}
// 		} catch (Exception e) {
// 			logger.error("get graph labels failed for: " + e.getMessage());
// 			return null;
// 		}
//
// 	}
//
// 	public List<String> dbPropertyKeys() {
// 		try {
// 			try (Session session = driver.session()) {
// 				List<String> propertyKeys = session.readTransaction(new TransactionWork<List<String>>() {
// 					@Override
// 					public List<String> execute(Transaction tx) {
// 						String cypher = "CALL db.propertyKeys YIELD propertyKey";
// 						List<String> propertyKeys = new ArrayList<>();
// 						StatementResult result = tx.run(cypher);
// 						while (result.hasNext()) {
// 							String propertyKey = result.next().get("propertyKey").asString();
// 							propertyKeys.add(propertyKey);
// 						}
// 						return propertyKeys;
// 					}
// 				});
// 				return propertyKeys;
// 			}
// 		} catch (Exception e) {
// 			logger.error("get graph property key failed for:" + e.getMessage());
// 			return null;
// 		}
// 	}
//
// 	public List<String> dbRelaLabels() {
// 		try {
// 			try (Session session = driver.session()) {
// 				List<String> relaTypes = session.readTransaction(new TransactionWork<List<String>>() {
// 					@Override
// 					public List<String> execute(Transaction tx) {
// 						//update by ws start
// 						String cypher = "CALL db.relationshipTypes YIELD relationshipType";
// 						//String cypher = "CALL db.relationshipTypes() YIELD relationshipType RETURN relationshipType limit 100";
// 						//update by ws/hn/pc endt
// 						List<String> relaTypes = new ArrayList<>();
// 						StatementResult result = tx.run(cypher);
// 						while (result.hasNext()) {
// 							String relaType = result.next().get("relationshipType").asString();
// 							relaTypes.add(relaType);
// 						}
// 						return relaTypes;
// 					}
// 				});
// 				return relaTypes;
// 			}
// 		} catch (Exception e) {
// 			logger.error("get graph relationship types failed for:" + e.getMessage());
// 			return null;
// 		}
// 	}
//
// 	public List<DbIndexesInfo> dbIndexs() {
// 		try {
// 			try (Session session = driver.session()) {
// 				List<DbIndexesInfo> dbIndexs = session.readTransaction(new TransactionWork<List<DbIndexesInfo>>() {
// 					@Override
// 					public List<DbIndexesInfo> execute(Transaction tx) {
// 						String cypher = "CALL db.indexes YIELD description, state, type";
// 						List<DbIndexesInfo> dbIndexs = new ArrayList<>();
// 						StatementResult result = tx.run(cypher);
// 						while (result.hasNext()) {
// 							Record record = result.next();
// 							String indexDes = record.get("description").asString();
// 							String indexState = record.get("state").asString();
// 							String indexType = record.get("type").asString();
// 							DbIndexesInfo dbIndex = new DbIndexesInfo(indexDes, indexState, indexType);
// 							dbIndexs.add(dbIndex);
// 						}
// 						return dbIndexs;
// 					}
// 				});
// 				return dbIndexs;
// 			}
// 		} catch (Exception e) {
// 			logger.error("get graph index failed for:" + e.getMessage());
// 			return null;
// 		}
// 	}
//
// 	@Override
// 	public Long getEntityNum() {
// 		return null;
// 	}
//
// 	@Override
// 	public Long getRelationNum() {
// 		return null;
// 	}
//
// 	@Override
// 	public List<String> getEntityLabels() {
// 		return null;
// 	}
//
// 	@Override
// 	public List<String> getRelationLabels() {
// 		return null;
// 	}
//
// 	@Override
// 	public List<String> getKeys() {
// 		return null;
// 	}
//
// 	@Override
// 	public List<DbIndexInfo> getDbIndexInfos() {
// 		return null;
// 	}
//
// 	@Override
// 	public ClusterDetailInfo getClusterDetailInfo() {
//
// 		//update start
//
// 		long entityNum = EntityNum();
// 		long relaNum = relaNum();
// 		List<String> entityLabels = dbLabels();
// 		List<String> relaLabels = dbRelaLabels();
// 		List<String> propertyKeys = dbPropertyKeys();
// 		List<DbIndexesInfo> indexs = dbIndexs();
// 		//System.out.println("start111111111111111111111111111111111111111111111");
// 		/*
// 		long entityNum = 0;
// 		long relaNum = 0;
// 		List<String> entityLabels = new ArrayList<String>();
// 		List<String> relaLabels = new ArrayList<String>();
// 		List<String> propertyKeys = new ArrayList<String>();
// 		List<DbIndexsInfo> indexs = new ArrayList<DbIndexsInfo>();
// 		*/
// 		//update end
//
// 		return new ClusterDetailInfo(null, entityNum, relaNum, entityLabels, relaLabels, propertyKeys, indexs);
// 	}
//
// 	@Override
// 	public boolean addEntity(Node entity) {
// 		return entityRepository.createEntity(entity);
// 	}
//
// 	@Override
// 	public boolean updateEntity(Node srcEntity, Node dstEntity) {
// 		return entityRepository.updateEntity(srcEntity, dstEntity);
// 	}
//
// 	@Override
// 	public boolean deleteEntity(String entityId) {
// 		return entityRepository.deleteEntity(entityId);
// 	}
//
// 	@Override
// 	public boolean containEntity(String entityId) {
// 		return entityRepository.isContainEntity(entityId);
// 	}
//
// 	@Override
// 	public Node findEntityByGraphId(long graphId) {
// 		return entityRepository.findByGraphId(graphId);
// 	}
//
// 	@Override
// 	public Node findEntityByEntityId(String entityId) {
// 		return entityRepository.findById(entityId);
// 	}
//
// 	@Override
// 	public List<Node> findEntityByLabels(List<String> labels) {
// 		return entityRepository.findByLabels(labels);
// 	}
//
// 	@Override
// 	public List<Node> findEntityByAttributes(Map<String, String> attributes) {
// 		return entityRepository.findByAttributes(attributes);
// 	}
//
// 	@Override
// 	public List<Node> findEntityByLabelsAndAttributes(List<String> labels, Map<String, String> attributes) {
// 		return entityRepository.findByLablesAndAttibutes(labels, attributes);
// 	}
//
// 	@Override
// 	public boolean addRelationship(Relationship crtRela) {
// 		return relationshipRepository.createRelationship(crtRela);
// 	}
//
// 	@Override
// 	public boolean updateRelationship(String srcRelaId, Relationship dstRela) {
// 		return false;
// 	}
//
// 	@Override
// 	public boolean deleteRelationship(String relaId) {
// 		return false;
// 	}
//
// 	@Override
// 	public boolean updateRela(String srcRelaId, Relationship dstRela) {
// 		return relationshipRepository.updateRelationship(srcRelaId, dstRela);
// 	}
//
// 	@Override
// 	public boolean deleteRela(String relaId) {
// 		return relationshipRepository.deleteRelationship(relaId);
// 	}
//
// 	@Override
// 	public boolean containRelationship(String relaId) {
// 		return relationshipRepository.isContainRelationship(relaId);
// 	}
//
// 	@Override
// 	public Relationship findRelaByRelaId(String relaId) {
// 		return relationshipRepository.findById(relaId);
// 	}
//
// 	@Override
// 	public List<Relationship> findRelaByLabel(String label) {
// 		return relationshipRepository.findyByLabels(label);
// 	}
//
// 	@Override
// 	public List<Relationship> findRelaByAttributes(Map<String, String> attributes) {
// 		return relationshipRepository.findByAttributes(attributes);
// 	}
//
// 	@Override
// 	public List<Relationship> findyRelaByLabelAndAttibutes(String label, Map<String, String> attributes) {
// 		return relationshipRepository.findByLabelsAndAttibutes(label, attributes);
// 	}
//
// 	@Override
// 	public List<Relationship> findRelaByStartEntity(Node startEntity) {
// 		return relationshipRepository.findByStartEntity(startEntity);
// 	}
//
// 	@Override
// 	public List<Relationship> findRelaByEndEntity(Node endEntity) {
// 		return relationshipRepository.findByEndEntity(endEntity);
// 	}
//
// 	@Override
// 	public List<Relationship> findRelationshipByEntity(Node from, Node to) {
// 		return null;
// 	}
//
// 	@Override
// 	public List<Relationship> findRelaByStartAndEntity(Node startEntity, Node endEntity) {
// 		return relationshipRepository.findByStartAndEnd(startEntity, endEntity);
// 	}
//
//
// 	@Override
// 	public Node findentitybyredis(String ID) {
// 			if(ID==null)
// 				return null;
// 			if(nodeDataRedisTemplate.hasKey(ID) == false)
// 			{
// 				System.out.println("redis have not id.");
// 				return null;
// 			}
// 			NodeDataInfo myNode = nodeDataRedisTemplate.opsForValue().get(ID);
// 			System.out.print("findentitybyredis: " + (myNode.getNodeId()));
// 			//System.out.print(ID);
// 			if(myNode != null)
// 				return new Node(ID,myNode.getLabels(),null);
// 			System.out.println("findentitybyredis return null.");
// 			return null;
// 	}
//
// 	/**
// 	 * 精准的匹配，星型图和对应得匹配图得节点数和边数一致
// 	 * @param starGraph
// 	 *            查询图分解后的星形查询图
// 	 * @return
// 	 */
// 	@Override
// 	public List<MatchGraph> starGraphMatch(StarGraph starGraph) {
// 		//System.out.println("starGraphMatch start--");
// 		List<MatchGraph> matchGraphs = new ArrayList<>();
//
// 		// 提取中心点候选集（候选节点的identifier）
// 		List<String> centerNodelabels = starGraph.getCenter().getLabels();
//
// 		//System.out.println("centerNodelabels: " + centerNodelabels);
//
// 		Set<String> centerCandidates = null;
// 		if(centerNodelabels == null || centerNodelabels.size() < 1)
// 			return matchGraphs;
// 		else
// 			centerCandidates = labelInexRedisTemplate.opsForSet().intersect(centerNodelabels.get(0), centerNodelabels);
//
// 		//System.out.println("centerCandidates num : " + centerCandidates.size());
// 		for (String centerCandidate : centerCandidates) {
// 			System.out.println(centerCandidate);
// 		}
// 		// 获取中心点的顶点编码
// 		NodeCode centerCode = starGraph.getNodeCode();//------这里拿到的编码是空的？！
//
// 		for (String candidate : centerCandidates) {
// 			NodeDataInfo candidateNode = nodeDataRedisTemplate.opsForValue().get(candidate);
// 			//System.out.println("candidateNode: " + candidateNode);
// 			// 根据标签合顶点编码信息过滤
// 			if (!candidateNode.getLabels().containsAll(centerNodelabels)
// 					|| candidateNode.getNodeCode().compareTo(centerCode) == -1) {
// 				//System.out.println("nodeCode.");
// 				continue;
// 			}
//
// 			// 得到邻居点的原始匹配（未展开），starGraph的邻居点对应多个candidateNode的邻居点，
// 			// neighborRawMatch key：queryId  value：匹配节点的identifier的List
// 			Map<Integer, List<String>> neighborRawMatch = new HashMap<>();
// 			for (NodeNeigh dataNeigh : candidateNode.getNeighbors()) {
// 				//System.out.println("dataNeigh");
// 				for (StarNeigh queryNeigh : starGraph.getNeighbors()) {
// 					int neighQueryId = queryNeigh.getQueryId();
// 					String neighDataId = dataNeigh.getNeighId();
// //					System.out.println("dataNeigh edgeLabel: " + dataNeigh.getEdgeLabel());
// //					System.out.println("queryNeigh edgeLabel: " + queryNeigh.getEdgeLabel());
// //					System.out.println("dataNeigh nodeLabels: " + dataNeigh.getNeighLabels());
// //					System.out.println("queryNeigh nodeLabel: " + queryNeigh.getNodeLabels());
// 					if (dataNeigh.getEdgeLabel().equals(queryNeigh.getEdgeLabel())
// 							&& dataNeigh.getNeighLabels().containsAll(queryNeigh.getNodeLabels())) { // 连接性测试
// 						System.out.println("判断通过。");
// 						List<String> neighMatch = null;
// 						if (neighborRawMatch.containsKey(neighQueryId))
// 							neighMatch = neighborRawMatch.get(neighQueryId);
// 						else
// 							neighMatch = new ArrayList<>();
// 						neighMatch.add(neighDataId);
// 						// 更新邻居点匹配集
// 						neighborRawMatch.put(neighQueryId, neighMatch);
// 					} // end if dataNeigh
// 				} // end for queryNeigh
// 			} // end for dataNeigh
//
// 			//System.out.println("neighborRawMatch: " + neighborRawMatch.toString());
// 			// 判断是否所有邻居点都具有匹配（只要一个邻居点没匹配点就可以终止）
// 			if (neighborRawMatch.size() != starGraph.getNeighbors().size())
// 				continue;
//
// 			// 每个查询节点都有至少一个匹配
// 			// 构造最初匹配图（中心点匹配对）
// 			MatchGraph matchGraph = new MatchGraph();
// 			matchGraph.addMapping(starGraph.getCenter().getQueryId(), candidateNode.getNodeId());
//
// 			// 展开邻居匹配集，queryId-identifier的可能不唯一，存在多种匹配图
// 			List<MatchGraph> tempMatchGraphs = new LinkedList<>();
// 			tempMatchGraphs.add(matchGraph);
//
// 			for (Map.Entry<Integer, List<String>> rawEntry : neighborRawMatch.entrySet()) {
// 				int neighQueryId = rawEntry.getKey();
// 				ListIterator<MatchGraph> iter = tempMatchGraphs.listIterator();
// 				while (iter.hasNext()) {
// 					MatchGraph curMatchGra = iter.next();
// 					iter.remove();
// 					for (String dataNodeId : rawEntry.getValue()) {
// 						MatchGraph nextMatchGraph = curMatchGra.deepCopy();
// 						nextMatchGraph.addMapping(neighQueryId, dataNodeId);
// 						iter.add(nextMatchGraph);
// 					} // end for dataNodeId
// 				} // end while iter
// 			} // end for rawEntry
//
// 			// 展开结果添加导最后结果
// 			matchGraphs.addAll(tempMatchGraphs);
// 		}
// 		//System.out.println("matchGraphs: " + matchGraphs.toString());
// 		return matchGraphs;
// 	}
//
//
//
// 	@Override
// 	public void findRedis(String ID) {
// 		System.out.println("server findjoneRedis start--");
//
// 		String res = template1.opsForValue().get(ID);
// 		System.out.println(res);
//
// 		return ;
// 	}
//
//
// 	//-----------------------------------------------时空查询-------------------------------------------------------
// 	//-----------------------------------------------Query.java start-------------------------------------------------------
//
// 	/**
// 	 * 对空间信息进行查询
// 	 * @param tree
// 	 * @param longitude1
// 	 * @param latitude1
// 	 * @param longitude2
// 	 * @param latitude2
// 	 * @return
// 	 */
// 	public static JSONArray query4location(RTree<ItemInfo, Point> tree, double longitude1, double latitude1, double longitude2, double latitude2){
//
// 		JSONArray rs = new JSONArray();
//
// 		Iterable<Entry<ItemInfo, Point>> its = tree.search(Geometries.rectangle(longitude1,latitude1,longitude2,latitude2))
// 				.toBlocking().toIterable();
//
// 		//将查询结果存到结果数组里
// 		for(Entry<ItemInfo, Point>it :its){
// 			JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(it.value()));
// 			rs.add(jsonObject);
//
// 		}
// 		return rs;
//
// 	}
//
//
// 	/**
// 	 * 对时间进行查询
// 	 * @param tree 已经建立的b+树
// 	 * @param start 起始时间
// 	 * @param end  终止时间
// 	 * @return
// 	 */
// 	public static JSONArray query4date(BPlusTree<Long,ItemInfo> tree, LocalDateTime start, LocalDateTime end){
//
// 		JSONArray rs = new JSONArray();
//
// 		tree.findEntries(start.toEpochSecond(ZoneOffset.of("+8")), end.toEpochSecond(ZoneOffset.of("+8"))).forEach(
// 				(K)-> {
// 					JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(K.value()));
// 					rs.add(jsonObject);
// 				}
// 		);
//
//
// 		return rs;
// 	}
//
// 	public static JSONArray query4curve(JSONArray origin,String key){
//
// 		JSONArray rs = new JSONArray();
//
// 		for(int i =0;i<origin.size();i++){
//
// 			JSONObject object = origin.getJSONObject(i);
//
// 			if(object.get("key").toString().equals(key)){
// 				rs.add(object);
// 			}
// 		}
//
//
// 		return rs;
//
// 	}
// 	public static JSONArray query4around(RTree<ItemInfo, Point> tree, double longitude1, double latitude1, double r){
//
// 		JSONArray rs = new JSONArray();
//
// 		Iterable<Entry<ItemInfo, Point>> its = tree.search(Geometries.point(longitude1,latitude1),r)
// 				.toBlocking().toIterable();
//
// 		for(Entry<ItemInfo, Point>it :its){
// 			JSONObject jsonObject = new JSONObject();
// 			jsonObject.put("key",it.value().getKey());
// 			jsonObject.put("datetime",it.value().getDatetime());
// 			jsonObject.put("longitude",it.value().getLongitude());
// 			jsonObject.put("latitude",it.value().getLatitude());
//
// 			rs.add(jsonObject);
// 		}
//
// 		return rs;
// 	}
//
// 	public static JSONArray query4RB(RTree<LocSliceInfo, Rectangle> br, LocalDateTime start, LocalDateTime end, double longitude1, double latitude1, double longitude2, double latitude2){
// 		JSONArray rs = new JSONArray();
//
// 		Iterable<Entry<LocSliceInfo, Rectangle>> its = br.search(Geometries.rectangle(longitude1,latitude1,longitude2,latitude2))
// 				.toBlocking().toIterable();
//
//
// 		for(Entry<LocSliceInfo, Rectangle>it :its){
//
// 			LocSliceInfo stamp = it.value();
// 			BPlusTree<Long,ItemInfo> btree = stamp.getBtree();
//
// 			JSONArray tempResult = query4date(btree,start,end);
//
// 			if(tempResult.size()!=0){
// 				for (int i =0;i<tempResult.size();i++){
// 					rs.add(tempResult.getJSONObject(i)); //将查询结果汇总到一个数组
// 				}
// 			}
//
// 		}
//
// 		return rs;
// 	}
//
// 	/**
// 	 * 使用时间和空间属性进行查询
// 	 * @param hr
// 	 * @param start
// 	 * @param end
// 	 * @param longitude1
// 	 * @param latitude1
// 	 * @param longitude2
// 	 * @param latitude2
// 	 * @return
// 	 */
// 	public static JSONArray query4hr(BPlusTree<Long, TimeSliceInfo> hr, LocalDateTime start, LocalDateTime end, double longitude1, double latitude1, double longitude2, double latitude2){
// 		JSONArray rs = new JSONArray();
//
// //        System.out.println("根据时间查询到的R树有：");
// 		//先进行时间查询，找到对应的r树树群
// 		hr.findEntries(start.toEpochSecond(ZoneOffset.of("+8")), end.toEpochSecond(ZoneOffset.of("+8"))).forEach(
// 				(K)-> {
// 					TimeSliceInfo info = K.value();
// 					RTree<ItemInfo, Point> rTree= info.getrTree();
//
// 					//对每颗r树都要查询
// 					JSONArray tempResult = query4location(rTree,longitude1,latitude1,longitude2,latitude2);
//
// 					//如果有查询结果
// 					if (tempResult.size()!=0){
//
// //                        System.out.println("时间片："+info.getDatetime());
// //                        System.out.println("R树MBR查询路线：");
//
// 						for (int i =0;i<tempResult.size();i++){
// 							rs.add(tempResult.getJSONObject(i)); //将查询结果汇总到一个数组
// 						}
//
// 					}
//
//
//
// 				}
// 		);
//
// 		return rs;
// 	}
//
// 	/**
// 	 * 调用它会输出查询路径
// 	 * @param hr
// 	 * @param start
// 	 * @param end
// 	 * @param longitude1
// 	 * @param latitude1
// 	 * @param longitude2
// 	 * @param latitude2
// 	 */
// 	public static void outputPath(BPlusTree<Long, TimeSliceInfo> hr, LocalDateTime start, LocalDateTime end, double longitude1, double latitude1, double longitude2, double latitude2){
//
// 		System.out.println("根据时间查询到的R树有：");
// 		//先进行时间查询，找到对应的r树树群
// 		hr.findEntries(start.toEpochSecond(ZoneOffset.of("+8")), end.toEpochSecond(ZoneOffset.of("+8"))).forEach(
// 				(K)-> {
// 					TimeSliceInfo info = K.value();
// 					RTree<ItemInfo, Point> rTree= info.getrTree();
//
// 					//对每颗r树都要查询
// 					JSONArray tempResult = query4location(rTree,longitude1,latitude1,longitude2,latitude2);
//
// 					//如果有查询结果
// 					if (tempResult.size()!=0){
//
// 						System.out.println("时间片："+info.getDatetime());
// 						System.out.println("R树MBR查询路线：");
//
// 						//针对每个查询结果打印轨迹
// 						String allPath = rTree.asString();
// 						selectOutput(allPath,tempResult);
// 					}
//
//
//
// 				}
// 		);
//
// 	}
//
// 	public static void selectOutput(String all,JSONArray rs){
//
//
// 		String [] results = all.split("\n");
//
// 		for (String line:results){
//
// 			if (line.contains("entry")){
//
// 				for(int i=0;i<rs.size();i++){
//
// 					if(line.contains("x="+rs.getJSONObject(i).get("longitude")+", y="+rs.getJSONObject(i).get("latitude"))){
// 						System.out.println(line);
// 					}
// 				}
//
// 			}else {
// 				System.out.println(line);
// 			}
//
// 		}
//
// 		System.out.println("------------------------------------------------------------");
// 	}
//
//
// 	//-----------------------------------------------Query.java end-------------------------------------------------------
//
// 	//-----------------------------------------------DataFile.java start-------------------------------------------------------
//
// 	/**
// 	 * 打开文件，返回文件流string
// 	 * @param filePath
// 	 * @return
// 	 */
// 	public static String open_file(String filePath){
// 		File f = new File(filePath);
// 		InputStream in =null;
// 		String fileStream="";
//
// 		try {
//
// 			if( f.isFile() &&  f.exists()){ //判断文件是否存在
// 				// 一次读多个字节
//
// 				BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(f),"utf-8"));
// 				String line="";
// 				while((line=br.readLine())!=null) {
// //                    System.out.println(line.length());
// 					fileStream+=line+"\n";
// 				}
// 			}else{
// 				System.out.println("找不到指定的文件,请确认文件路径是否正确");
// 			}
//
//
//
// 		} catch (Exception e) {
// 			System.out.println("读取文件内容出错");
// 			e.printStackTrace();
// 		} finally {
// 			if (in != null) {
// 				try {
// 					in.close();
// 				} catch (IOException e1) {
// 				}
// 			}
// 		}
//
// 		return fileStream;
// 	}
//
//
// 	/**
// 	 * 读取文件，返回操作结果
// 	 * @param filePath
// 	 * @param str
// 	 * @return
// 	 */
// 	public static boolean writeFile(String filePath, String str) {
// 		FileWriter fw;
// 		try {
//
// 			fw = new FileWriter(filePath);
// 			PrintWriter out = new PrintWriter(fw);
// 			out.write(str);
// 			out.println();
// 			fw.close();
// 			out.close();
// 			return true;
// 		} catch (IOException e) {
// 			e.printStackTrace();
// 			return false;
// 		}
//
// 	}
//
// 	public static String open_file_line(String filePath){
// 		File f = new File(filePath);
// 		InputStream in =null;
// 		String fileStream="";
//
// 		try {
//
// 			if( f.isFile() &&  f.exists()){ //判断文件是否存在
// 				// 一次读多个字节
//
// 				BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(f),"utf-8"));
//
// 				while((fileStream=br.readLine())!=null) {
// //                    System.out.println(line.length());
// 				}
// 			}else{
// 				System.out.println("找不到指定的文件,请确认文件路径是否正确");
// 			}
//
//
//
// 		} catch (Exception e) {
// 			System.out.println("读取文件内容出错");
// 			e.printStackTrace();
// 		} finally {
// 			if (in != null) {
// 				try {
// 					in.close();
// 				} catch (IOException e1) {
// 				}
// 			}
// 		}
//
// 		return fileStream;
// 	}
//
//
//
// 	//-----------------------------------------------DataFile.java end-------------------------------------------------------
//
// 	//-----------------------------------------------CreateIndex.java start-------------------------------------------------------
//
// 	/**
// 	 * 根据json数组创建空间索引R树
// 	 * @param origin 原始数据，即json数组
// 	 * @return 创建的R树
// 	 */
// 	public static RTree<ItemInfo, Point> createLocIndex(JSONArray origin){
//
// 		RTree<ItemInfo, Point> tree =  RTree.star().maxChildren(6).create();
// 		// --- 创建r树索引文件 ------
// 		for(int i =0;i<origin.size();i++){
//
// 			JSONObject object = origin.getJSONObject(i);
//
// 			double longitude = object.getDouble("longitude");
// 			double latitude = object.getDouble("latitude");
// 			Point lc = Geometries.point(longitude,latitude);
//
// 			tree=tree.add(new ItemInfo(object.get("key").toString(), LocalDateTime.parse(object.get("datetime").toString()),longitude,latitude),lc);
// 		}
//
//
//
// 		return tree;
//
// 	}
//
// 	/**
// 	 * 根据json数组创建时间B树
// 	 * @param origin 原始数据，即json数组
// 	 * @return 创建的B树
// 	 */
// 	public static BPlusTree<Long,ItemInfo> createTimeIndex(JSONArray origin){
//
// 		BPlusTree<Long, ItemInfo> tree =
// 				BPlusTree
// 						.memory()
// 						.maxLeafKeys(32)
// 						.maxNonLeafKeys(8)
// 						.naturalOrder();
//
// 		for(int i =0;i<origin.size();i++){
//
// 			JSONObject object = origin.getJSONObject(i);
// 			double longitude = object.getDouble("longitude");
// 			double latitude = object.getDouble("latitude");
// 			LocalDateTime dt = LocalDateTime.parse(object.get("datetime").toString());
// 			tree.insert(dt.toEpochSecond(ZoneOffset.of("+8")),new ItemInfo(object.get("key").toString(),dt,longitude,latitude));
// 		}
//
//
// 		return tree;
// 	}
//
// 	/**
// 	 * 根据json数组创建，以时间的B树为基，B树节点挂着每个时间片的R树（时间片为一天）
// 	 * @param origin 原始数据，即json数组
// 	 * @return 创建的B树
// 	 */
// 	public static BPlusTree<Long, TimeSliceInfo> createHRIndex(JSONArray origin){
// 		BPlusTree<Long, TimeSliceInfo> tree =
// 				BPlusTree
// 						.memory()
// 						.maxLeafKeys(32)
// 						.maxNonLeafKeys(8)
// 						.naturalOrder();
//
// 		//将数据的时间分为不同的时间片,这里假设以天作为分片单位
// 		Map<LocalDate,ArrayList<ItemInfo>> dateSet = new HashMap<>();
//
// 		for(int i =0;i<origin.size();i++){
//
// 			JSONObject object = origin.getJSONObject(i);
//
// 			double longitude = object.getDouble("longitude");
// 			double latitude = object.getDouble("latitude");
// 			LocalDateTime dt = LocalDateTime.parse(object.get("datetime").toString());
//
// 			LocalDate date = dt.toLocalDate(); //抽取时间片
//
// 			if(dateSet.get(date)!=null){
// 				ArrayList<ItemInfo> sliceList = dateSet.get(date);
// 				sliceList.add(new ItemInfo(object.get("key").toString(),dt,longitude,latitude));
// 				dateSet.put(date,sliceList);
// 			}else {
// 				ArrayList<ItemInfo> sliceList = new ArrayList<>();
// 				sliceList.add(new ItemInfo(object.get("key").toString(),dt,longitude,latitude));
// 				dateSet.put(date,sliceList);
// 			}
// 		}
//
// //        System.out.println("BR子树大小为："+dateSet.size());
//
// 		//根据每个时间片内的空间数据构建R树
// 		Iterator<Map.Entry<LocalDate, ArrayList<ItemInfo>>> iter = dateSet.entrySet().iterator();
// 		Map.Entry<LocalDate, ArrayList<ItemInfo>> entry;
//
// 		while (iter.hasNext()) {
// 			entry = iter.next();
// 			ArrayList<ItemInfo> stampList = entry.getValue();
//
// 			JSONArray temp = JSONArray.parseArray(JSON.toJSONString(stampList));
// 			RTree<ItemInfo, Point> rTree = createLocIndex(temp);
// 			LocalDate dt = entry.getKey();
//
// 			TimeSliceInfo stampInfo = new TimeSliceInfo(dt,rTree);
//
// 			LocalDateTime dt2ldt = LocalDateTime.of(dt.getYear(),dt.getMonth(),dt.getDayOfMonth(),0,0);
// 			tree.insert(dt2ldt.toEpochSecond(ZoneOffset.of("+8")),stampInfo);
// 		}
//
// 		return tree;
//
// 	}
//
// 	/**
// 	 * 根据json数组创建，以空间的R树为基，R树节点挂着每个地点片的B树（空间片为(X,Y)=[x,y,x+1,y+1]）
// 	 * @param origin 原始数据，即json数组
// 	 * @return 创建的R树
// 	 */
// 	public static RTree<LocSliceInfo, Rectangle> createRBtree(JSONArray origin){
// 		RTree<LocSliceInfo, Rectangle> tree =  RTree.star().maxChildren(6).create();
//
// 		//以区域作为分割片，假设这里分割间距为
// 		Map<Rectangle,ArrayList<ItemInfo>> dateSet = new HashMap<>();
//
// 		for (int i=0;i<origin.size();i++){
//
// 			JSONObject object = origin.getJSONObject(i);
// 			//解析object的数据格式
// 			double longitude = object.getDouble("longitude");
// 			double latitude = object.getDouble("latitude");
// 			LocalDateTime dt = LocalDateTime.parse(object.get("datetime").toString());
//
// 			//抽取整数值为空间片
// 			int long_int = (int)longitude;
// 			int lati_int = (int)latitude;
// 			double delta=0.1;//无穷小
//
// 			//*这里需要加一个低于最小精度的变量，因为比如搜索[0,10]，而10.xx会被归为[10,11]类，从而与[0,10]相交而被选择，
// 			//故需要把10.xx归到(10,11]类
// 			Rectangle rec = Geometries.rectangle(long_int+delta,lati_int+delta,long_int+1,lati_int+1);
//
// 			if(dateSet.get(rec)!=null){
//
// 				ArrayList<ItemInfo> sliceList = dateSet.get(rec);
// 				sliceList.add(new ItemInfo(object.get("key").toString(),dt,longitude,latitude));
// 				dateSet.put(rec,sliceList);
// 			}else {
// 				ArrayList<ItemInfo> sliceList = new ArrayList<>();
// 				sliceList.add(new ItemInfo(object.get("key").toString(),dt,longitude,latitude));
// 				dateSet.put(rec,sliceList);
// 			}
//
// 		}
//
// //        System.out.println("RB的子树个数为："+dateSet.size());
//
// 		//根据每个空间片内的时间数据构建B树
// 		Iterator<Map.Entry<Rectangle,ArrayList<ItemInfo>>> iter = dateSet.entrySet().iterator();
// 		Map.Entry<Rectangle, ArrayList<ItemInfo>> entry;
//
// 		while (iter.hasNext()) {
// 			entry = iter.next();
// 			ArrayList<ItemInfo> stampList = entry.getValue();
//
// 			JSONArray temp = JSONArray.parseArray(JSON.toJSONString(stampList));
// 			BPlusTree<Long,ItemInfo> bTree = createTimeIndex(temp); //构建B树
//
// 			LocSliceInfo locStampInfo = new LocSliceInfo(entry.getKey(),bTree);
//
// 			tree = tree.add(locStampInfo,entry.getKey());
// 		}
//
// 		return tree;
// 	}
//
// 	//-----------------------------------------------CreateIndex.java end-------------------------------------------------------
//
//
// 	//-----------------------------------------------时空查询接口-------------------------------------------------------
// 	//-----------------------------------------------QuickHrQuery.java start-------------------------------------------------------
//
// 	private BPlusTree<Long, TimeSliceInfo> index;
//
// 	public BPlusTree<Long, TimeSliceInfo> getIndex() {
// 		return index;
// 	}
//
// 	@Override
// 	public String quickQuery(LocalDateTime start, LocalDateTime end, double longitude1, double latitude1, double longitude2, double latitude2){
//
// 		createTimeSpaceIndex("./kg-server/src/main/resources/testData.json");
// 		//System.out.println("index大小："+ RamUsageEstimator.sizeOf(getIndex()));
// 		//createTimeSpaceIndex("./testData.json");
// 		Objects.requireNonNull(index,"请先创建索引");
// 		//outputPath(index,start,end,longitude1,latitude1,longitude2,latitude2);
//
// //		String result = query4hr(index,start,end,longitude1,latitude1,longitude2,latitude2).toString();
// //		System.out.println(result);
// //		return result;
// 		return query4hr(index,start,end,longitude1,latitude1,longitude2,latitude2).toString();
// 	}
//
// 	public void createTimeSpaceIndex(String fileName){
//
// 		String origin = open_file(fileName);
// 		index = createHRIndex(JSONArray.parseArray(origin));
// 	}
//
// 	private void createIndex(JSONArray jsonArray){
// 		index = createHRIndex(jsonArray);
// 	}
//
// 	@Override
// 	public void outputPath(LocalDateTime start, LocalDateTime end, double longitude1, double latitude1, double longitude2, double latitude2){
// 		if (index == null){
// 			System.out.println("请先创建索引");
// 		}
// 		outputPath(index,start,end,longitude1,latitude1,longitude2,latitude2);
// 	}
//
//
// 	//-----------------------------------------------QuickHrQuery.java end-------------------------------------------------------
//
// 	@Override
// 	public void testRmi(String str){
// 		System.out.println(str + " test success.");
// 	}
// }

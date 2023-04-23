# 正在进行的工作

+   为了对边提供无限的扩展能力，将relation也作为node进行存储，
+   完成和节点相关的Neo4j操作，



# 接下来的工作





```
* TODO: 使用@CompositeProperty注解, 得到的集合Set<String>中的实际上是neo4j中StringValue类型, 想要类型转换感觉由没有asString()方法, 直接当做String来使用又出现报错.
*  尝试使用@Property注解, 此时在添加relationship, 即调用relationshipRepository.save()时发生报错: java.lang.IllegalStateException: Required identifier property not found for class java.lang.String
```

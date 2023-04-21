// package org.dml.service;
//
// import lombok.AllArgsConstructor;
// import lombok.Data;
// import org.dml.properties.RmiServiceProperties;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.remoting.rmi.RmiProxyFactoryBean;
// import org.springframework.stereotype.Component;
//
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;
// import java.util.Set;
//
// @Component
// @Data
// @AllArgsConstructor
// public class IntegrateServiceProxy {
//
//     @Autowired
//     RmiServiceProperties rmiServiceProperties;
//
//
//     /**
//      * 机器名（IP地址）-- 图谱操作服务映射
//      */
//     private Map<String, GraphDataService> services = new HashMap<>();
//
//     /**
//      * 根据RMI服务配置类初始化RMI服务代理
//      */
//     public IntegrateServiceProxy() {
//
//         List<String> hosts = rmiServiceProperties.getHosts();
//         for (int i = 0; i < rmiServiceProperties.getNumber(); i++) {
//             RmiProxyFactoryBean rmiProxy = new RmiProxyFactoryBean();
//             // 拼接一个rmi的ServiceUrl
//             String rmiServiceUrl = new StringBuilder()
//                     .append("rmi://")
//                     .append(hosts.get(i))
//                     .append(":")
//                     .append(rmiServiceProperties.getPort())
//                     .append("/")
//                     .append(rmiServiceProperties.getName())
//                     .toString();
//
//             rmiProxy.setServiceUrl(rmiServiceUrl);
//             rmiProxy.setServiceInterface(GraphDataService.class);
//             rmiProxy.afterPropertiesSet();
//             GraphDataService service = (GraphDataService) rmiProxy.getObject();
//
//             // 每台主机都注册一个GraphDataService, 这样做的意义是什么？
//             services.put(hosts.get(i), service);
//         }
//     }
//
//
//     /**
//      * 获取RMI服务代理的所有主机IP
//      *
//      * @return RMI服务代理的所有主机IP
//      */
//     public Set<String> getHosts() {
//         return this.services.keySet();
//     }
//
//     /**
//      * 根据机器IP地址获取Neo4j图数据图操作服务
//      *
//      * @param host 机器IP地址
//      * @return 图谱操作服务
//      */
//     public GraphDataService getServiceByHost(String host) {
//         return this.services.get(host);
//     }
//
// }
//

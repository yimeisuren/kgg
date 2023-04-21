// package org.dml.properties;
//
// import lombok.Data;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.stereotype.Component;
//
// import java.util.List;
//
// @Component
// @Data
// public class RmiServiceProperties {
//
//     @Value("${rmi.service.name}")
//     private String name;
//
//     /**
//      * 布置RMI服务的机器数
//      */
//     @Value("${rmi.service.number}")
//     private int number;
//
//     /**
//      * todo: 可能需要换成列表
//      */
//     @Value("${rmi.service.hosts}")
//     private List<String> hosts;
//
//     /**
//      * 服务暴露端口
//      */
//     @Value("${rmi.service.port}")
//     private int port;
//
// }

// package org.dml.config;
//
// import org.dml.service.GraphDataService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.remoting.rmi.RmiServiceExporter;
//
// @Configuration
// public class RmiConfig {
//
//     @Autowired
//     GraphDataService graphDataService;
//     @Bean
//     public RmiServiceExporter rmiServiceExporter() {
//         RmiServiceExporter rmiExporter = new RmiServiceExporter();
//         rmiExporter.setService(graphDataService);
//         rmiExporter.setServiceInterface(graphDataService.getClass());
//         rmiExporter.setRegistryPort(9000);
//         rmiExporter.setServiceName("gk");
//         rmiExporter.setServicePort(9000);
//         return rmiExporter;
//     }
//
//
//
// }

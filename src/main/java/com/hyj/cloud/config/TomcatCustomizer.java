//package com.hyj.cloud.config;
//
//import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
//import org.springframework.boot.web.server.WebServerFactoryCustomizer;
//import org.springframework.stereotype.Component;
//
//@Component
//public class TomcatCustomizer implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {
//
//    @Override
//    public void customize(TomcatServletWebServerFactory factory) {
//        factory.addConnectorCustomizers(connector -> {
//            connector.setMaxPostSize(10 * 1024 * 1024); // 10 MB
//        });
//    }
//}

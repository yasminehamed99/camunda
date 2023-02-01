package com.example.workflow.config;

import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootConfiguration
public class ClearanceCoreConfig {

    @Value("${process.xsdLocation}")
    private String xsdLocation;

    @Value("${process.enRulesLocation}")
    private String enRulesLocation;

    @Value("${process.ksaRulesLocation}")
    private String ksaRulesLocation;



    @Bean(name = "xsdFile")
    public Path getXSDFile() {
        return Paths.get(xsdLocation);
    }

    @Bean(name = "enRulesFile")
    public Path getENRulesFile() {
        return Paths.get(enRulesLocation);
    }

    @Bean(name = "ksaRulesFile")
    public Path getKSARulesFile() {
        return Paths.get(ksaRulesLocation);
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> tomcatCustomizer() {
        return (tomcat) -> tomcat.addConnectorCustomizers((connector) -> {
            if (connector.getProtocolHandler() instanceof AbstractHttp11Protocol) {
                AbstractHttp11Protocol<?> protocolHandler = (AbstractHttp11Protocol<?>) connector
                        .getProtocolHandler();

                protocolHandler.setMaxKeepAliveRequests(500);
                protocolHandler.setUseKeepAliveResponseHeader(true);
            }
        });
    }


}

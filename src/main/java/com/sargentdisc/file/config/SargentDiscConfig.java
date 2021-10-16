package com.sargentdisc.file.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.sargentdisc.file.controller.DirectoryFileSearchController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

@Configuration
public class SargentDiscConfig {
    private ApplicationContext ctx;
    private DirectoryFileSearchController controller;

    SargentDiscConfig(ApplicationContext ctx, DirectoryFileSearchController controller){
        this.ctx = ctx;
        this.controller = controller;
    }
    @Bean
    public SimpleUrlHandlerMapping simpleUrlHandlerMapping() {
        SimpleUrlHandlerMapping simpleUrlHandlerMapping
                = new SimpleUrlHandlerMapping();

        Map<String, Object> urlMap = new HashMap<>();
        urlMap.put("/sargent-disc/*/file/search*", this.controller);
        urlMap.put("/sargent-disc/*/file/content*", this.controller);
        simpleUrlHandlerMapping.setUrlMap(urlMap);

        return simpleUrlHandlerMapping;
    }
}

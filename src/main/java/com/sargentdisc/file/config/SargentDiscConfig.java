package com.sargentdisc.file.config;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sargentdisc.file.controller.DirectoryFileSearchController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;

//@EnableWebMvc
//@Configuration
public class SargentDiscConfig {
    private ApplicationContext ctx;
    private DirectoryFileSearchController controller;

    SargentDiscConfig(ApplicationContext ctx, DirectoryFileSearchController controller) {
        this.ctx = ctx;
        this.controller = controller;
    }

    @Bean
    HttpRequestHandler httpRequestHandler() {
        return new MyHttpRequestHandler();
    }

    @Bean
    public SimpleUrlHandlerMapping simpleUrlHandlerMapping() {
        SimpleUrlHandlerMapping simpleUrlHandlerMapping
                = new SimpleUrlHandlerMapping();

        Map<String, Object> urlMap = new HashMap<>();
        urlMap.put("/sargent-disc/v1.0/file/search", controller);
        urlMap.put("/sargent-disc/v1.0/file/content", controller);
        simpleUrlHandlerMapping.setUrlMap(urlMap);

        return simpleUrlHandlerMapping;
    }

    public class MyHttpRequestHandler implements HttpRequestHandler {

        @Override
        public void handleRequest(HttpServletRequest request,
                                  HttpServletResponse response) throws ServletException, IOException {
            PrintWriter writer = response.getWriter();
            writer.write("response from MyHttpRequestHandler, uri: " + request.getRequestURI());
        }
    }
}

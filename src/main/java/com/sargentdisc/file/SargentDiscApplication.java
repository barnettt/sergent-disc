package com.sargentdisc.file;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//        (exclude = {DataSourceAutoConfiguration.class,
//        HibernateJpaAutoConfiguration.class,
//        DataSourceTransactionManagerAutoConfiguration.class})
// @ComponentScan(basePackages = {"com.sargent.disc.file.controller","com.sargent.disc.file.reader"})
public class SargentDiscApplication {

    public static void main(String[] args) {
        SpringApplication.run(SargentDiscApplication.class, args);
    }
}

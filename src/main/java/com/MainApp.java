package com;

import com.service.siteMapBuilder.SiteMapBuilder;
import org.apache.commons.logging.Log;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.service")
public class MainApp {

    public static void main(String[] args) {
        SpringApplication.run(MainApp.class);
    }
}

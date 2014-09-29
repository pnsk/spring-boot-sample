package com.pnsk.app.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.logging.log4j.Log4JLoggingSystem;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.util.Log4jConfigurer;

import java.io.FileNotFoundException;

/**
 * When you launch this application, you have to execute "main" method.
 * Created by okuda_junko on 14/09/16.
 */
@EnableAutoConfiguration
@ComponentScan({"com.pnsk"})
public class Application {


    public static void main(String[] args) {

        try {
            Log4jConfigurer.initLogging(Log4jConfigurer.CLASSPATH_URL_PREFIX + "log4j.properties");
        } catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }
        SpringApplication.run(Application.class, args);
        System.out.println("run start:)");

    }
}

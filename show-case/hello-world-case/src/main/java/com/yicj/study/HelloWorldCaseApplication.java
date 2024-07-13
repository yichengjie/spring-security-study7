package com.yicj.study;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity(debug = true)
@SpringBootApplication
public class HelloWorldCaseApplication {

    public static void main(String[] args) {

        SpringApplication.run(HelloWorldCaseApplication.class, args) ;
    }

}
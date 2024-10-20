package com.yicj.study;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MapperScan("com.yicj.study.mapper")
@SpringBootApplication
public class HelloTestApplication {

    public static void main(String[] args) {

        SpringApplication.run(HelloTestApplication.class, args) ;
    }
}

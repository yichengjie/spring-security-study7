package com.yicj.study.mybatis;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.yicj.study.mybatis.mapper")
public class HelloMybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloMybatisApplication.class, args) ;
    }
}

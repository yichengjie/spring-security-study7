package com.yicj.study.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.yicj.study.mapper")
public class MybatisConfig {

}

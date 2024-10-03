package com.yicj.elasticsearch.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.yicj.elasticsearch.mapper")
public class MybatisConfig {

}

package com.yicj.study.mybatis

import com.yicj.study.mybatis.model.Person
import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
@MapperScan("com.yicj.study.mybatis.mapper")
open class HelloMybatisApplication

fun main(args: Array<String>) {
    SpringApplication.run(HelloMybatisApplication::class.java, *args)
}
package com.yicj.study.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * HelloController
 * </p>
 *
 * @author yicj
 * @since 2024年07月13日 15:42
 */
@Slf4j
@RestController
@RequestMapping("/hello")
public class HelloController {

    @Autowired
    private ApplicationContext context ;

    @GetMapping("/index")
    public String hello(){
        String beanName = "org.springframework.security.config.annotation.web.configuration.HttpSecurityConfiguration.httpSecurity" ;
        HttpSecurity bean = context.getBean(beanName, HttpSecurity.class);
        log.info("bean : {}", bean);
        return "hello world" ;
    }

    @GetMapping("/other")
    public String other(){
        String beanName = "org.springframework.security.config.annotation.web.configuration.HttpSecurityConfiguration.httpSecurity" ;
        HttpSecurity bean = context.getBean(beanName, HttpSecurity.class);
        log.info("bean : {}", bean);
        return "hello other" ;
    }

}

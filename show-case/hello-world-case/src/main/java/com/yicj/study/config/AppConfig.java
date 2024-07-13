package com.yicj.study.config;

import com.yicj.study.service.HelloService;
import com.yicj.study.service.impl.HelloServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * AppConfig
 * </p>
 *
 * @author yicj
 * @since 2024年07月13日 20:53
 */
@Configuration
public class AppConfig {

    @Bean("com.yicj.study.config.HelloService.helloService")
    public HelloService helloService(){
        return new HelloServiceImpl() ;
    }

    @Bean
    public HelloService helloService2(){
        return new HelloServiceImpl() ;
    }
}

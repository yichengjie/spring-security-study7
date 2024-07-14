package com.yicj.study.config;

import com.yicj.study.service.HelloService;
import com.yicj.study.service.UserService;
import com.yicj.study.service.impl.HelloServiceImpl;
import com.yicj.study.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * <p>
 * AppConfig
 * </p>
 *
 * @author yicj
 * @since 2024年07月13日 20:53
 */
@Slf4j
@Configuration
public class AppConfig {

    @Scope("prototype")
    @Bean(name = "com.yicj.study.config.HelloService.helloService")
    public HelloService helloService(){
        log.info("init prototype bean HelloService ........");
        return new HelloServiceImpl() ;
    }

//    @Bean
//    public HelloService helloService2(){
//        log.info("init singleton bean HelloService2 ........");
//        return new HelloServiceImpl() ;
//    }


    @Bean
    public UserService userService(HelloService helloService){
        log.info("init singleton bean UserService ........");
        return new UserServiceImpl() ;
    }
}

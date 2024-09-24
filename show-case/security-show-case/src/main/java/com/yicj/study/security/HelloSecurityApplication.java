package com.yicj.study.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 * HelloSecurityApplication
 * </p>
 *
 * @author yicj
 * @since 2024/08/31 20:31
 */
// https://zhuanlan.zhihu.com/p/626276527 //DelegatingFilterProxyRegistrationBean#getFilter
@SpringBootApplication
public class HelloSecurityApplication {
    public static void main(String[] args) {

        SpringApplication.run(HelloSecurityApplication.class, args) ;
    }
}

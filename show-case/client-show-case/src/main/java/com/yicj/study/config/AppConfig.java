package com.yicj.study.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.yicj.study.filter.TenantFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.problem.jackson.ProblemModule;

/**
 * @author yicj
 * @since 2024/9/25
 */
@Configuration
public class AppConfig {

    @Bean
    public FilterRegistrationBean<TenantFilter> tenantFilterRegistration(TenantFilter filter) {
        FilterRegistrationBean<TenantFilter> registration = new FilterRegistrationBean<>(filter);
        registration.setEnabled(false);
        return registration;
    }

    @Autowired
    public void objectMapper(ObjectMapper objectMapper){
        objectMapper.registerModule(new ProblemModule());
    }
}

package com.yicj.study.config;

import com.yicj.study.component.LoggerBuilder;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogConfig {

    @Autowired
    LoggerBuilder loggerBuilder;

    @Bean
    public Logger loggerBean(){
        return loggerBuilder.getLogger();
    }
}

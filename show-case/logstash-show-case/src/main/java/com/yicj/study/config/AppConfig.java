package com.yicj.study.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yicj.study.properties.AppProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * AppConfig
 * </p>
 *
 * @author yicj
 * @since 2024年07月27日 11:02
 */
@Configuration
@EnableConfigurationProperties({AppProperties.class})
public class AppConfig {

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper() ;
    }
}

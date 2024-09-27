package com.yicj.study.r2dbc.config;

import io.asyncer.r2dbc.mysql.MySqlConnectionConfiguration;
import io.asyncer.r2dbc.mysql.MySqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;

/**
 * @author yicj
 * @since 2024/9/11 22:06
 */
//@Configuration
//public class ApplicationConfiguration extends AbstractR2dbcConfiguration {
//
//    @Bean
//    @Override
//    public ConnectionFactory connectionFactory() {
//        MySqlConnectionConfiguration configuration = MySqlConnectionConfiguration.builder()
//                .host("localhost")
//                .port(3306)
//                .username("root")
//                .password("root")
//                .database("study")
//                .build();
//       return MySqlConnectionFactory.from(configuration);
//    }
//}

package com.yicj.study.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import javax.sql.DataSource;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

/**
 * <p>
 * AppConfig
 * </p>
 *
 * @author yicj
 * @since 2024/8/15 22:42
 */
@Configuration
public class AppConfig {

    @Bean
    DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(H2)
                .addScript("sqls/create-table-user.sql")
                .build();
    }
}

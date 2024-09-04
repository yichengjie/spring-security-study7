package com.yicj.study.jpa.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * <p>
 * TestServiceConfiguration
 * </p>
 *
 * @author yicj
 * @since 2024/08/18 19:17
 */
@AutoConfigureDataJpa
@Import({LiquibaseConfig.class, JpaConfig.class, MybatisConfig.class})
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan(value = {"com.yicj.study.jpa.service"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TestServiceConfiguration {
    @Bean
    public DataSource dataSource() {
        var builder = new EmbeddedDatabaseBuilder() ;
        return builder.setType(EmbeddedDatabaseType.H2).build() ;
    }
}

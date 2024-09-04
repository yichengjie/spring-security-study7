package com.yicj.study.jpa.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * <p>
 * JpaConfig
 * </p>
 *
 * @author yicj
 * @since 2024/08/18 19:10
 */
@Configuration
@EnableTransactionManagement
@EntityScan(basePackages = "com.yicj.study.jpa.entity")
@EnableJpaRepositories(basePackages = "com.yicj.study.jpa.repository")
public class JpaConfig {

}

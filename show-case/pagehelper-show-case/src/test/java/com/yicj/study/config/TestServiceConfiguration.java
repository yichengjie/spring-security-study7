package com.yicj.study.config;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * <p>
 * TestServiceConfiguration
 * </p>
 *
 * @author yicj
 * @since 2024/08/17 12:30
 */
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan({"com.yicj.study"})
@EntityScan(basePackages = "com.yicj.study.mapper")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureDataJpa
@EnableTransactionManagement
@ImportAutoConfiguration
public class TestServiceConfiguration {

}

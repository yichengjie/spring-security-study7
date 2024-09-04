package com.yicj.study.jpa.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * MybatisConfig
 * </p>
 *
 * @author yicj
 * @since 2024/08/18 16:06
 */
@MapperScan("com.yicj.study.jpa.mapper")
@Configuration
public class MybatisConfig {

}

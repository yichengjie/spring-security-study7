package com.yicj.study.logstash;

import com.yicj.study.logstash.config.LogstashConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * <p>
 * SssLogstashAutoConfiguration
 * </p>
 *
 * @author yicj
 * @since 2024年07月27日 14:36
 */
@Configuration
@Import(LogstashConfig.class)
public class AscLogstashAutoConfiguration {

}

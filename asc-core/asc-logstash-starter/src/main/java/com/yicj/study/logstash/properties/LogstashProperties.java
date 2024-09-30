package com.yicj.study.logstash.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>
 * LogstashProperties
 * </p>
 *
 * @author yicj
 * @since 2024年07月27日 10:55
 */
@Data
@ConfigurationProperties(prefix = "sss.logstash")
public class LogstashProperties {

    private String host ;

    private String port ;

    private int queueSize ;

    private boolean enabled ;

    private String trustStoreLocation ;

    private String trustStorePassword ;

    private String networkInterfaceName ;
}

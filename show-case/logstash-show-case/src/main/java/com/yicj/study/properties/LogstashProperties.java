package com.yicj.study.properties;

import lombok.Data;

/**
 * <p>
 * LogstashProperties
 * </p>
 *
 * @author yicj
 * @since 2024年07月27日 10:55
 */
@Data
public class LogstashProperties {

    private String host ;

    private String port ;

    private int queueSize ;

    private boolean enabled ;

    private String trustStoreLocation ;

    private String trustStorePassword ;

    private String networkInterfaceName ;
}

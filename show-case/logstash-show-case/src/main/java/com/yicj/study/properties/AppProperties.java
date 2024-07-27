package com.yicj.study.properties;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Optional;

/**
 * <p>
 * AppProperties
 * </p>
 *
 * @author yicj
 * @since 2024年07月27日 10:57
 */
@Data
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    @Value("${spring.application.name}")
    private String appName ;

    @Value("${server.port}")
    private String serverPort ;

    @Value("${spring.profiles.active}")
    private String profileActive ;

    private LogstashProperties logstash ;


    public String getProfileActive() {
        return Optional.ofNullable(profileActive)
                .filter(StringUtils::isNoneBlank)
                .orElse("dev");
    }
}

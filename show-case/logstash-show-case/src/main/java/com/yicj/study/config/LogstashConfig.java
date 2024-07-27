package com.yicj.study.config;

import ch.qos.logback.classic.AsyncAppender;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.net.ssl.KeyStoreFactoryBean;
import ch.qos.logback.core.net.ssl.SSLConfiguration;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yicj.study.properties.AppProperties;
import com.yicj.study.properties.LogstashProperties;
import net.logstash.logback.appender.LogstashTcpSocketAppender;
import net.logstash.logback.encoder.LogstashEncoder;
import net.logstash.logback.stacktrace.ShortenedThrowableConverter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * LoggingConfiguration
 * </p>
 *
 * @author yicj
 * @since 2024年07月27日 10:45
 */
@Configuration
public class LogstashConfig implements InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(LogstashConfig.class);

    @Autowired
    private AppProperties appProperties ;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.init(this.appProperties);
    }

    private void init(AppProperties appProperties) throws JsonProcessingException {
        LogstashProperties logstash = appProperties.getLogstash();
        if (!logstash.isEnabled() || StringUtils.isBlank(logstash.getHost())) {
            log.info("[LogstashConfig] Stash log disabled.");
            return;
        }
        ch.qos.logback.classic.Logger rootLogger = (ch.qos.logback.classic.Logger)LoggerFactory.getLogger("ROOT");
        LoggerContext loggerContext = rootLogger.getLoggerContext();
        Map<String, String> map = new HashMap<>();
        map.put("app_env", appProperties.getProfileActive());
        map.put("app_name", appProperties.getAppName());
        map.put("app_port", appProperties.getServerPort());
        String customFields = new ObjectMapper().writeValueAsString(map);
        LogstashEncoder logstashEncoder = new LogstashEncoder();
        logstashEncoder.setCustomFields(customFields);
        ShortenedThrowableConverter throwableConverter = new ShortenedThrowableConverter();
        throwableConverter.setRootCauseFirst(true);
        logstashEncoder.setThrowableConverter(throwableConverter);
        logstashEncoder.setContext(loggerContext);
        logstashEncoder.setIncludeCallerData(true);
        SSLConfiguration sslConfiguration = null;
        if (StringUtils.isNotBlank(logstash.getTrustStoreLocation())) {
            sslConfiguration = new SSLConfiguration();
            KeyStoreFactoryBean factory = new KeyStoreFactoryBean();
            factory.setLocation(logstash.getTrustStoreLocation());
            if (logstash.getTrustStorePassword() != null) {
                factory.setPassword(logstash.getTrustStorePassword());
            }
            sslConfiguration.setTrustStore(factory);
        }
        LogstashTcpSocketAppender logstashTcpSocketAppender = new LogstashTcpSocketAppender();
        logstashTcpSocketAppender.setName("LOGSTASH");
        logstashTcpSocketAppender.setContext(loggerContext);
        logstashTcpSocketAppender.addDestination(logstash.getHost() + ":" + logstash.getPort());
        logstashTcpSocketAppender.setEncoder(logstashEncoder);
        logstashTcpSocketAppender.setIncludeCallerData(true);
        if (sslConfiguration != null) {
            logstashTcpSocketAppender.setSsl(sslConfiguration);
        }
        logstashTcpSocketAppender.start();
        AsyncAppender asyncLogstashAppender = new AsyncAppender();
        asyncLogstashAppender.setContext(loggerContext);
        asyncLogstashAppender.setName("ASYNC_LOGSTASH");
        asyncLogstashAppender.setQueueSize(logstash.getQueueSize());
        asyncLogstashAppender.addAppender(logstashTcpSocketAppender);
        asyncLogstashAppender.setIncludeCallerData(true);
        asyncLogstashAppender.start();
        rootLogger.addAppender(asyncLogstashAppender);
        log.info("[LogstashConfig] Stash log init done.");
    }

}

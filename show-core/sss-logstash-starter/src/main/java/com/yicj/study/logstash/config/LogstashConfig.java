package com.yicj.study.logstash.config;

import ch.qos.logback.classic.AsyncAppender;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.net.ssl.KeyStoreFactoryBean;
import ch.qos.logback.core.net.ssl.SSLConfiguration;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yicj.study.logstash.properties.LogstashProperties;
import net.logstash.logback.appender.LogstashTcpSocketAppender;
import net.logstash.logback.encoder.LogstashEncoder;
import net.logstash.logback.stacktrace.ShortenedThrowableConverter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * <p>
 * LoggingConfiguration
 * </p>
 * @author yicj
 * @since 2024年07月27日 10:45
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({LogstashProperties.class})
public class LogstashConfig implements InitializingBean, EnvironmentAware {
    private final Logger log = LoggerFactory.getLogger(LogstashConfig.class);

    private Environment env ;
    @Autowired
    private LogstashProperties logstashProperties ;

    @Override
    public void afterPropertiesSet() throws Exception {
        String appEnv = Optional.ofNullable(env.getProperty("spring.profiles.active")).orElse("dev") ;
        String appName = Optional.ofNullable(env.getProperty("spring.application.name")).orElse("unknown") ;
        String appPort = Optional.ofNullable(env.getProperty("server.port")).orElse("8080") ;
        this.init(appEnv, appName, appPort);
    }

    private void init(String appEnv, String appName, String appPort) throws JsonProcessingException {
        if (!logstashProperties.isEnabled() || StringUtils.isBlank(logstashProperties.getHost())) {
            log.info("[LogstashConfig] Stash log disabled.");
            return;
        }
        //ch.qos.logback.classic.Logger rootLogger = (ch.qos.logback.classic.Logger)LoggerFactory.getLogger("ROOT");
        //LoggerContext loggerContext = rootLogger.getLoggerContext();
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory() ;
        Map<String, String> map = new HashMap<>();
        map.put("app_env", appEnv) ;
        map.put("app_name", appName) ;
        map.put("app_port", appPort) ;
        String customFields = new ObjectMapper().writeValueAsString(map);
        LogstashEncoder logstashEncoder = new LogstashEncoder();
        logstashEncoder.setCustomFields(customFields);
        ShortenedThrowableConverter throwableConverter = new ShortenedThrowableConverter();
        throwableConverter.setRootCauseFirst(true);
        logstashEncoder.setThrowableConverter(throwableConverter);
        logstashEncoder.setContext(loggerContext);
        logstashEncoder.setIncludeCallerData(true);
        SSLConfiguration sslConfiguration = null;
        if (StringUtils.isNotBlank(logstashProperties.getTrustStoreLocation())) {
            sslConfiguration = new SSLConfiguration();
            KeyStoreFactoryBean factory = new KeyStoreFactoryBean();
            factory.setLocation(logstashProperties.getTrustStoreLocation());
            if (logstashProperties.getTrustStorePassword() != null) {
                factory.setPassword(logstashProperties.getTrustStorePassword());
            }
            sslConfiguration.setTrustStore(factory);
        }
        LogstashTcpSocketAppender logstashTcpSocketAppender = new LogstashTcpSocketAppender();
        logstashTcpSocketAppender.setName("LOGSTASH");
        logstashTcpSocketAppender.setContext(loggerContext);
        logstashTcpSocketAppender.addDestination(logstashProperties.getHost() + ":" + logstashProperties.getPort());
        logstashTcpSocketAppender.setEncoder(logstashEncoder);
        logstashTcpSocketAppender.setIncludeCallerData(true);
        if (sslConfiguration != null) {
            logstashTcpSocketAppender.setSsl(sslConfiguration);
        }
        logstashTcpSocketAppender.start();
        AsyncAppender asyncLogstashAppender = new AsyncAppender();
        asyncLogstashAppender.setContext(loggerContext);
        asyncLogstashAppender.setName("ASYNC_LOGSTASH");
        asyncLogstashAppender.setQueueSize(logstashProperties.getQueueSize());
        asyncLogstashAppender.addAppender(logstashTcpSocketAppender);
        asyncLogstashAppender.setIncludeCallerData(true);
        asyncLogstashAppender.start();
        loggerContext.getLogger("ROOT").addAppender(asyncLogstashAppender);
        log.info("[LogstashConfig] Stash log init done.");
    }


    @Override
    public void setEnvironment(Environment environment) {
        this.env = environment ;
    }
}

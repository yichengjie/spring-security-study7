package com.yicj.study.component;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.ConsoleAppender;
import com.yicj.study.enums.LogAction;
import net.logstash.logback.appender.LogstashTcpSocketAppender;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * LoggerBuilder主要用于创建logger类。创建步骤如下
 * 1. 获取logger上下文
 * 2. 从上下文获取logger对象。创建过的logger会保存在LOGCONTAINER中，保证下次获取logger不会重复创建。这里使用ConcurrentHashMap防止出现并发问题。
 * 3. 创建appender，并将appender加入logger对象中。
 */
@Component
public class LoggerBuilder {
    @Autowired
    AppenderBuilder appenderBuilder;

    @Value("${spring.application.name:unknow-system}")
    private String appName;

    private static final Map<String, Logger> LOGCONTAINER = new ConcurrentHashMap<>();

    public Logger getLogger(LogAction logAction) {
        Logger logger = LOGCONTAINER.get(logAction.getActionName() + "-" + appName);
        if (logger != null) {
            return logger;
        }
        logger = build(logAction);
        LOGCONTAINER.put(logAction.getActionName() + "-" + appName, logger);
        return logger;
    }

    public Logger getLogger() {
        return getLogger(LogAction.CUSTON_ACTION);
    }

    private Logger build(LogAction logAction) {
        //创建日志appender
        List<LogstashTcpSocketAppender> list = createAppender(appName, logAction.getActionName());
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger logger = context.getLogger(logAction.getActionName() + "-" + appName);
        logger.setAdditive(false);
        //打印控制台appender
        ConsoleAppender consoleAppender = appenderBuilder.consoleAppenderBuild();
        logger.addAppender(consoleAppender);
        list.forEach(logger::addAppender);
        return logger;
    }

    /**
     * LoggerContext上下文中的日志对象加入appender
     */
    public void addContextAppender() {
        //创建四种类型日志
        String action = LogAction.SYS_ACTION.getActionName();
        List<LogstashTcpSocketAppender> list = createAppender(appName, action);
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        //打印控制台
        ConsoleAppender consoleAppender = appenderBuilder.consoleAppenderBuild();
        context.getLoggerList().forEach(logger -> {
            logger.setAdditive(false);
            logger.addAppender(consoleAppender);
            list.forEach(logger::addAppender);
        });
    }

    /**
     * 创建连接elk的appender，每一种级别日志创建一个appender
     *
     * @param name
     * @param action
     * @return
     */
    public List<LogstashTcpSocketAppender> createAppender(String name, String action) {
        List<LogstashTcpSocketAppender> list = new ArrayList<>();
        LogstashTcpSocketAppender errorAppender = appenderBuilder.logAppenderBuild(name, action, Level.ERROR);
        LogstashTcpSocketAppender infoAppender = appenderBuilder.logAppenderBuild(name, action, Level.INFO);
        LogstashTcpSocketAppender warnAppender = appenderBuilder.logAppenderBuild(name, action, Level.WARN);
        LogstashTcpSocketAppender debugAppender = appenderBuilder.logAppenderBuild(name, action, Level.DEBUG);
        LogstashTcpSocketAppender traceAppender = appenderBuilder.logAppenderBuild(name, action, Level.TRACE);
        list.add(errorAppender);
        list.add(infoAppender);
        list.add(warnAppender);
        list.add(debugAppender);
        list.add(traceAppender);
        return list;
    }
}

package com.yicj.study.component;

import ch.qos.logback.access.PatternLayoutEncoder;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.filter.LevelFilter;
import ch.qos.logback.core.ConsoleAppender;
import net.logstash.logback.appender.LogstashTcpSocketAppender;
import net.logstash.logback.encoder.LogstashEncoder;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

import static ch.qos.logback.core.spi.FilterReply.ACCEPT;
import static ch.qos.logback.core.spi.FilterReply.DENY;

/**
 * 使用编程式配置logback，AppenderBuilder用于创建appender
 * 这里会创建两种appender。consoleAppender负责将日志打印到控制台，这对开发来说是十分有用的。而LogstashTcpSocketAppender则负责将日志保存到ELK中
 * setCustomFields中的参数，对应上面logstash配置文件的参数[appname]和[action]。
 */
@Component
public class AppenderBuilder {

    public static final String SOCKET_ADDRESS = "你的虚拟机ip地址";

    public static final Integer PORT = 5043;//logstash tcp输入端口

    /**
     * logstash通信Appender
     * @param name
     * @param action
     * @param level
     * @return
     */
    public LogstashTcpSocketAppender logAppenderBuild(String name, String action, Level level) {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        LogstashTcpSocketAppender appender = new LogstashTcpSocketAppender();
        appender.setContext(context);
        //设置logstash通信地址
        InetSocketAddress inetSocketAddress = new InetSocketAddress(SOCKET_ADDRESS, PORT);
        appender.addDestinations(inetSocketAddress);
        LogstashEncoder logstashEncoder = new LogstashEncoder();
        //对应前面logstash配置文件里的参数
        logstashEncoder.setCustomFields("{\"appname\":\"" + name + "\",\"action\":\"" + action + "\"}");
        appender.setEncoder(logstashEncoder);

        //这里设置级别过滤器
        LevelFilter levelFilter = new LevelFilter();
        levelFilter.setLevel(level);
        levelFilter.setOnMatch(ACCEPT);
        levelFilter.setOnMismatch(DENY);
        levelFilter.start();
        appender.addFilter(levelFilter);
        appender.start();

        return appender;
    }
    
    
    /**
     * 控制打印Appender
     * @return
     */
    public ConsoleAppender consoleAppenderBuild() {
        ConsoleAppender consoleAppender = new ConsoleAppender();
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(context);
        //设置格式
        encoder.setPattern("%red(%d{yyyy-MM-dd HH:mm:ss}) %green([%thread]) %highlight(%-5level) %boldMagenta(%logger) - %cyan(%msg%n)");
        encoder.start();
        consoleAppender.setEncoder(encoder);
        consoleAppender.start();
        return consoleAppender;
    }

}

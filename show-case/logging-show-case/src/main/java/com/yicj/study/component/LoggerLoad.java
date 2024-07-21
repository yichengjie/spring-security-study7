package com.yicj.study.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value = 1)
@Slf4j
public class LoggerLoad implements ApplicationRunner {
    @Autowired
    LoggerBuilder loggerBuilder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        loggerBuilder.addContextAppender();
        log.info("加载日志模块成功");
    }
}

package com.yicj.study.r2dbc.service;

import com.yicj.study.r2dbc.entity.UserReact;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

// 使用@Service注解，将该类标记为Spring框架的服务组件
@Service
// 使用@Slf4j注解，自动为该类生成一个SLF4J日志记录器
@Slf4j
// 使用@RequiredArgsConstructor注解，自动生成一个构造函数，用于初始化final字段
@RequiredArgsConstructor
public class InitService {
    // 注入UserService依赖
    private final UserService userService;

    // 使用@Transactional注解，确保方法内的操作在一个事务中执行
    @Transactional
    // 使用@EventListener注解，监听ApplicationReadyEvent事件，当事件发生时执行该方法
    @EventListener(classes = ApplicationReadyEvent.class)
    public Mono<Void> onApplicationReadyEvent() {
        // 定义一个账户名
        String account = "admin";
        // 调用userService的getUser方法，尝试获取指定账户的用户信息
        return userService.getUser(account)
            // 如果用户不存在（返回的Mono为空），则执行以下操作
            .switchIfEmpty(Mono.defer(() -> {
                // 创建一个新的UserReact对象，设置相关属性
                UserReact user = UserReact.builder()
                        .name(account)
                        .account(account)
                        .role(UserReact.ROLE_ADMIN)
                        .build();
                // 调用userService的addUser方法，添加新用户并返回其Mono
                return userService.addUser(user);
            })).then(); // 最后返回一个完成的Mono<Void>
    }
}
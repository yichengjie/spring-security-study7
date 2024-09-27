package com.yicj.study.r2dbc.service;// 导入相关依赖和服务注解
import com.yicj.study.r2dbc.entity.UserReact;
import com.yicj.study.r2dbc.enums.Code;
import com.yicj.study.r2dbc.exception.XException;
import com.yicj.study.r2dbc.repository.UserRepository;
import com.yicj.study.r2dbc.utils.CommonUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

// 定义UserService类，用于处理用户相关的业务逻辑
@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository; // 注入UserRepository，用于访问数据库中的用户数据

    // 根据账号获取用户信息的方法
    public Mono<UserReact> getUser(String account) {
        return userRepository.findByAccount(account); // 调用userRepository的findByAccount方法查询用户
    }
 
    // 根据用户ID获取用户信息的方法
    public Mono<UserReact> getUserById(String uid) {
        return userRepository.findById(uid); // 调用userRepository的findById方法查询用户
    }
 
    // 添加用户的方法，使用事务注解确保操作的原子性
    @Transactional
    public Mono<UserReact> addUser(UserReact user) {
        return userRepository.findByAccount(user.getAccount()) // 先检查账号是否已存在
            .handle((u, sink) ->
                sink.error(XException.builder() // 如果已存在，则抛出异常
                    .codeN(Code.ERROR)
                    .message("用户已存在")
                    .build())
            )
            .cast(UserReact.class) // 将结果转换为UserReact类型
            .switchIfEmpty(Mono.defer(() -> { // 如果不存在，则创建新用户
                user.setId(CommonUtil.uuid());
                user.setPassword(user.getAccount()); // 对密码进行加密
                return userRepository.save(user); // 保存用户到数据库
            }));
    }
 
    // 根据角色获取用户列表的方法
    public Mono<List<UserReact>> listUsers(String role) {
        return userRepository.findByRole(role).collectList(); // 调用userRepository的findByRole方法查询用户列表并收集为List
    }
}
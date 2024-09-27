package com.yicj.study.r2dbc.controller;

import com.yicj.study.r2dbc.entity.UserReact;
import com.yicj.study.r2dbc.model.vo.RequestConstant;
import com.yicj.study.r2dbc.model.vo.ResultVO;
import com.yicj.study.r2dbc.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.util.Map;

// 定义一个名为AdminController的控制器类，用于处理与管理员相关的API请求
@RestController
// 使用Slf4j注解，为该类提供日志记录功能
@Slf4j
// 使用RequiredArgsConstructor注解，自动生成构造函数，要求所有final字段都必须被初始化
@RequiredArgsConstructor
// 设置该控制器的基础URL路径为"/api/admin/"
@RequestMapping("/api/admin/")
public class AdminController {
 
    // 注入UserService实例，用于处理用户相关的业务逻辑
    private final UserService userService;
 
    // 处理POST请求，创建新用户
    @PostMapping("users")
    public Mono<ResultVO<Void>> postUsers(@RequestBody UserReact user) {
        // 调用userService的addUser方法添加用户，并返回一个包含成功信息的ResultVO对象
        return userService.addUser(user)
                .thenReturn(ResultVO.success());
    }
 
    // 处理GET请求，获取用户信息
    @GetMapping("info")
    public Mono<ResultVO<Map<String, UserReact>>> getInfo(@RequestAttribute(RequestConstant.UID) String uid) {
        // 调用userService的getUserById方法根据用户ID获取用户信息，并将其包装在ResultVO对象中返回
        return userService.getUserById(uid)
            .map(user -> ResultVO.success(Map.of("user", user)));
    }
}
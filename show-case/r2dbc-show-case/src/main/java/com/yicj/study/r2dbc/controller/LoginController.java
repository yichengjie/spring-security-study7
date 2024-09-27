package com.yicj.study.r2dbc.controller;

import com.yicj.study.r2dbc.entity.UserReact;
import com.yicj.study.r2dbc.enums.Code;
import com.yicj.study.r2dbc.model.vo.RequestConstant;
import com.yicj.study.r2dbc.model.vo.ResultVO;
import com.yicj.study.r2dbc.service.UserService;
import com.yicj.study.r2dbc.support.JWTComponent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

// 定义一个名为LoginController的类，用于处理登录相关的请求
@RestController
@Slf4j // 使用Lombok库提供的日志功能
@RequiredArgsConstructor // 使用Lombok库提供的构造器注入功能
@RequestMapping("/api/") // 设置该控制器处理的请求的基本路径为"/api/"
public class LoginController {
    private final UserService userService; // 用户服务组件，用于获取用户信息
    private final JWTComponent jwtComponent; // JSON Web Token组件，用于生成和解析JWT令牌
 
    // 处理POST请求，映射到"/login"路径，用于用户登录
    @PostMapping("login")
    public Mono<ResultVO<Map<String, UserReact>>> login(@RequestBody UserReact user, ServerHttpResponse response) {
        // 从userService中获取用户信息，根据用户的账号进行筛选
        return userService.getUser(user.getAccount())
            // 检查用户提供的密码是否与数据库中的密码匹配
            .filter(u -> StringUtils.equals(user.getPassword(),u.getPassword()))
            // 如果密码匹配成功，则执行以下操作
            .map(u -> {
                // 创建一个包含用户ID和角色信息的Map对象
                Map<String, Object> tokenM = Map.of(
                        RequestConstant.UID, u.getId(),
                        RequestConstant.ROLE, u.getRole()
                );
                // 使用jwtComponent对tokenM进行编码，生成JWT令牌
                String token = jwtComponent.encode(tokenM);
                // 获取响应头对象
                HttpHeaders headers = response.getHeaders();
                // 将生成的JWT令牌添加到响应头的"token"字段中
                headers.add("token", token);
                // 将用户的角色添加到响应头的"role"字段中
                headers.add("role", u.getRole());
                // 返回一个表示成功的ResultVO对象，其中包含用户信息
                return ResultVO.success(Map.of("user", u));
            })
            // 如果密码不匹配或用户不存在，则返回一个表示登录错误的ResultVO对象
            .defaultIfEmpty(ResultVO.error(Code.LOGIN_ERROR));
    }
}
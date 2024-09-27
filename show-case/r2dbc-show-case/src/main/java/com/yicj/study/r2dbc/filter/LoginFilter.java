package com.yicj.study.r2dbc.filter;// 导入相关依赖
import com.yicj.study.r2dbc.enums.Code;
import com.yicj.study.r2dbc.model.vo.RequestConstant;
import com.yicj.study.r2dbc.support.JWTComponent;
import com.yicj.study.r2dbc.support.ResponseHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

// 定义一个登录过滤器类，实现WebFilter接口
@Component
@Slf4j
@Order(1)
@RequiredArgsConstructor
public class LoginFilter implements WebFilter {
    // 定义需要过滤的路径模式
    private final PathPattern path = new PathPatternParser().parse("/api/**");
    // 定义不需要过滤的路径模式列表
    private final List<PathPattern> excludesS = List.of(new PathPatternParser().parse("/api/login"));
    // 注入JWT组件
    private final JWTComponent jwtComponent;
    // 注入响应帮助类
    private final ResponseHelper responseHelper;
 
    // 重写filter方法
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        // 获取请求对象
        ServerHttpRequest request = exchange.getRequest();
        // 遍历排除列表，如果请求路径匹配排除列表中的任何一个，直接放行
        for (PathPattern p : excludesS) {
            if (p.matches(request.getPath().pathWithinApplication())) {
                return chain.filter(exchange);
            }
        }
        // 如果请求路径不在过滤范围内，返回异常响应
        if (!path.matches(request.getPath().pathWithinApplication())) {
            return responseHelper.response(Code.BAD_REQUEST, exchange);
        }
        // 从请求头中获取token
        String token = request.getHeaders().getFirst(RequestConstant.TOKEN);
        // 如果token为空，返回未授权响应
        if (token == null) {
            return responseHelper.response(Code.UNAUTHORIZED, exchange);
        }
        // 解码token，并将解码结果放入请求属性中
        return jwtComponent.decode(token)
            .flatMap(decode -> {
                Map<String, Object> attributes = exchange.getAttributes();
                attributes.put(RequestConstant.UID, decode.getClaim(RequestConstant.UID).asString());
                attributes.put(RequestConstant.ROLE, decode.getClaim(RequestConstant.ROLE).asString());
                // 继续执行后续过滤器链
                return chain.filter(exchange);
            });
    }
}
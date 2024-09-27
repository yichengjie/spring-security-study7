package com.yicj.study.r2dbc.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yicj.study.r2dbc.enums.Code;
import com.yicj.study.r2dbc.model.vo.ResultVO;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

// 定义一个名为ResponseHelper的类，用于处理响应
@Component
@Slf4j
@RequiredArgsConstructor
public class ResponseHelper {
    // 使用ObjectMapper对象进行JSON序列化
    private final ObjectMapper objectMapper;
 
    // 定义一个response方法，接收Code枚举类型和一个ServerWebExchange对象作为参数
    @SneakyThrows
    public Mono<Void> response(Code code, ServerWebExchange exchange) {
        // 将错误信息转换为JSON字符串并编码为UTF-8字节数组
        byte[] bytes = objectMapper.writeValueAsString(ResultVO.error(code))
                .getBytes(StandardCharsets.UTF_8);
        // 获取服务器响应对象
        ServerHttpResponse response = exchange.getResponse();
        // 将字节数组包装成DataBuffer对象
        DataBuffer wrap = response.bufferFactory().wrap(bytes);
        // 设置响应内容类型为JSON
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        // 将DataBuffer写入响应并返回Mono<Void>对象
        return response.writeWith(Flux.just(wrap));
    }
}
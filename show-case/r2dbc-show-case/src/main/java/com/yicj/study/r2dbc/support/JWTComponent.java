package com.yicj.study.r2dbc.support;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.yicj.study.r2dbc.enums.Code;
import com.yicj.study.r2dbc.exception.XException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;


@Component // 标记为Spring组件，使其可以被自动扫描并注入到其他类中
@Slf4j // 使用Lombok库提供的日志功能，简化日志记录操作
public class JWTComponent {
    // 私钥，用于签名和验证JWT令牌
    @Value("${app.secretkey}")
    private String secretkey;
 
    /**
     * 对给定的负载数据进行编码，生成一个JWT令牌。
     * @param map 包含有效载荷数据的Map对象
     * @return 返回编码后的JWT令牌字符串
     */
    public String encode(Map<String, Object> map) {
        // 设置令牌过期时间为当前时间加一个月
        LocalDateTime time = LocalDateTime.now().plusMonths(1);
        return JWT.create()
                .withPayload(map) // 添加有效载荷数据
                .withIssuedAt(new Date()) // 设置令牌签发时间
                .withExpiresAt(Date.from(time.atZone(ZoneId.systemDefault()).toInstant())) // 设置令牌过期时间
                .sign(Algorithm.HMAC256(secretkey)); // 使用HMAC256算法和私钥进行签名
    }
 
    /**
     * 解码给定的JWT令牌，验证其有效性并返回解码后的有效载荷。
     * @param token 要解码的JWT令牌字符串
     * @return 返回一个包含解码后有效载荷的Mono对象
     */
    public Mono<DecodedJWT> decode(String token) {
        try {
            // 使用指定的算法和私钥验证并解码JWT令牌
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(secretkey)).build().verify(token);
            return Mono.just(decodedJWT); // 如果验证成功，返回解码后的有效载荷
        } catch (TokenExpiredException | SignatureVerificationException | JWTDecodeException e) {
            Code code = Code.FORBIDDEN; // 默认错误代码为禁止访问
            if (e instanceof TokenExpiredException) {
                code = Code.TOKEN_EXPIRED; // 如果令牌已过期，则设置相应的错误代码
            }
            return Mono.error(XException.builder().code(code).build()); // 返回一个包含错误信息的Mono对象
        }
    }
}
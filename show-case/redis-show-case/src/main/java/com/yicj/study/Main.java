package com.yicj.study;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Slf4j
public class Main {
    public static void main(String[] args) {
        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory();
        connectionFactory.setHostName("localhost");
        connectionFactory.setPort(6379);
        connectionFactory.afterPropertiesSet();

        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setDefaultSerializer(StringRedisSerializer.UTF_8);
        template.afterPropertiesSet();

        template.opsForValue().set("foo", "bar");

        log.info("Value at foo:" + template.opsForValue().get("foo"));

        connectionFactory.destroy();
    }
}
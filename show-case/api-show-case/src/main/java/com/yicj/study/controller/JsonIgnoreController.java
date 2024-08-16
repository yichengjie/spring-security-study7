package com.yicj.study.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * JsonIgnoreController
 * </p>
 *
 * @author yicj
 * @since 2024/08/16 18:28
 */
@Slf4j
@RestController
@RequestMapping("/json/ignore")
public class JsonIgnoreController {

    @PostMapping("/create")
    public UserInfo create(@RequestBody UserInfo info){
        log.info("info : {}", info);
        return info ;
    }

    // JsonIgnore 注解添加之后，序列化与反序列化都会忽略该字段
    @Data
    public static class UserInfo {
        public int id;
        public String name;

        @JsonIgnore
        public String password;

        @JsonIgnore
        private UserAddress userAddress ;
    }

    @Data
    public static class UserAddress{

        private String userId ;

        private String address ;
    }
}

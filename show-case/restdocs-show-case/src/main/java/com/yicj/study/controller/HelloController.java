package com.yicj.study.controller;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yicj
 * @since 2024/10/11 22:08
 */
@RestController
@RequestMapping("/hello")
public class HelloController {

    @RequestMapping("/index")
    public String index(HelloReq req){
        return "hello" ;
    }


    @Data
    static class HelloReq{
        private String name ;
        private String age ;
    }

}

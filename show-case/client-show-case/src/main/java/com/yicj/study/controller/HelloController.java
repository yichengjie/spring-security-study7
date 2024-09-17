package com.yicj.study.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yicj
 * @since 2024/9/17 11:26
 */
@Slf4j
@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping("/cookies")
    public String cookies(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if (cookies == null){
            log.info("no cookies");
            return "no cookies" ;
        }
        for (Cookie cookie : cookies){
            log.info("cookie name: {}, cookie value； {}", cookie.getName(), cookie.getValue());
        }
        return "hello cookies" ;
    }


    @GetMapping("/header")
    public String header(HttpServletRequest request){
        String header = request.getHeader("X-Request-Id");
        log.info("header X-Request-Id : {}", header);
        return "hello header" ;
    }

    @GetMapping("/addHeader")
    public String addHeader(HttpServletRequest request){
        String header = request.getHeader("X-Request-Acme");
        log.info("header X-Request-Acme : {}", header);
        return "hello header" ;
    }

    // addParam
    @GetMapping("/addParam")
    public String addParam(HttpServletRequest request){
        String param = request.getParameter("name");
        log.info("param name : {}", param);
        return "hello param" ;
    }

    @GetMapping("/other")
    public String other(){

        return "hello other" ;
    }

}
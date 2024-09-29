package com.yicj.study.springdoc.controller;


import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Enumeration;

@Slf4j
@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping("/index")
    public String index(HttpServletRequest request){
        Enumeration<String> headerNames = request.getHeaderNames();
        // print all headers
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            log.info(headerName + " : " + request.getHeader(headerName));
        }
        return "Hello World" ;
    }
}

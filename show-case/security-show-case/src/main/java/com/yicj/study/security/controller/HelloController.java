package com.yicj.study.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * HelloController
 * </p>
 *
 * @author yicj
 * @since 2024/08/31 21:12
 */
@RestController
@RequestMapping("/api/hello")
public class HelloController {

    @GetMapping("/index")
    public String index(){
        return "hello security" ;
    }
}

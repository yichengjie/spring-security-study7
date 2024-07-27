package com.yicj.study.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * HelloController
 * </p>
 *
 * @author yicj
 * @since 2024年07月27日 13:21
 */
@Slf4j
@RestController
public class HelloController {

    @GetMapping("/hello/index")
    public String index(){
        log.info("hello logstash") ;
        return "hello" ;
    }

}

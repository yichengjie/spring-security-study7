package com.yicj.study.kafka.controller;

import com.yicj.study.kafka.producer.HelloProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yicj
 * @since 2024/9/20 22:20
 */
@RestController
@RequestMapping("/hello")
@RequiredArgsConstructor
public class HelloController {

    private final HelloProducer helloProducer ;

    @GetMapping("/send")
    public String send(@RequestParam("msg") String msg){
        helloProducer.send(msg);
        return "success" ;
    }
}

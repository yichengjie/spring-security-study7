package com.yicj.study.controller;


import com.yicj.study.service.HelloService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/hello")
public class HelloController {

    private final HelloService helloService ;

    @RequestMapping("/index")
    public String index(){
        return helloService.index() ;
    }
}

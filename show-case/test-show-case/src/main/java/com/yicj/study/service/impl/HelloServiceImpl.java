package com.yicj.study.service.impl;


import com.yicj.study.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class HelloServiceImpl implements HelloService {

    @Override
    public String index() {
        log.info("HelloServiceImpl.index");
        return "Hello World" ;
    }
}

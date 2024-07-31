package com.yicj.study.service.impl;

import com.yicj.study.service.HelloService;
import org.springframework.stereotype.Service;

@Service
public class HelloServiceImpl implements HelloService {

    @Override
    public String slow(int secondNum) {
        try {
            Thread.sleep(secondNum * 1000L);
        } catch (InterruptedException e) {
            // ignore
        }
        return "slow value";
    }
}

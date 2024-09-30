package com.yicj.study.mybatis.service;


import com.yicj.study.mybatis.HelloMybatisApplication;
import com.yicj.study.mybatis.entity.UserReact;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest(classes = HelloMybatisApplication.class)
class UserReactServiceTest {

    @Autowired
    private UserReactService userReactService ;

    @Test
    void selectByName() {
        String name = "yicj" ;
        List<UserReact> list = userReactService.selectByName(name);
        list.forEach(item -> log.info("item : {}", item));
    }

}

package com.yicj.study.mybatis.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.yicj.study.mybatis.HelloMybatisApplication;
import com.yicj.study.mybatis.entity.UserReact;
import com.yicj.study.mybatis.model.User;
import com.yicj.study.mybatis.support.CurrentUserHolder;
import com.yicj.study.mybatis.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

@Transactional
@Slf4j
@SpringBootTest(classes = HelloMybatisApplication.class)
class UserReactServiceTest {

    @Autowired
    private UserReactService userReactService ;

    @BeforeEach
    void init(){
        User user = new User() ;
        user.setUserName("yicj");
        user.setAdmin(false);
        user.setShops(List.of("shop1", "shop2"));
        CurrentUserHolder.setUser(user);
    }

    @Test
    void selectByName() {
        String name = "yicj" ;
        List<UserReact> list = userReactService.selectByName(name);
        list.forEach(item -> log.info("item : {}", item));
    }

    @Test
    void batchInsert(){
        List<UserReact> list = IntStream.range(1, 20)
            .mapToObj(i -> {
                UserReact userReact = new UserReact();
                userReact.setId(CommonUtil.uuid());
                userReact.setName("yicj" + i);
                userReact.setAccount("yicj" + i);
                userReact.setPassword("123456");
                userReact.setRole("admin");
                userReact.setInsertTime(LocalDateTime.now());
                userReact.setUpdateTime(LocalDateTime.now());
                return userReact;
            }).toList();
        userReactService.saveBatch(list, 8);
    }

    @Test
    void selectOne(){
        LambdaQueryChainWrapper<UserReact> wrapper = new LambdaQueryChainWrapper<>(UserReact.class);
        UserReact entity = wrapper.eq(UserReact::getName, "yicj")
                .select(UserReact::getName, UserReact::getAccount)
                .one();
        log.info("entity : {}", entity);
    }

}

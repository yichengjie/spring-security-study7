package com.yicj.study.mybatis.service;


import com.yicj.study.mybatis.HelloMybatisApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Transactional
@SpringBootTest(classes = HelloMybatisApplication.class)
class SimpleQueryTest {

//    void list() {
//        // 假设有一个 User 实体类和对应的 BaseMapper
//        List<Long> ids = SimpleQuery.list(
//                Wrappers.lambdaQuery(User.class), // 使用 lambda 查询构建器
//                User::getId, // 提取的字段，这里是 User 的 id
//                System.out::println, // 第一个 peek 操作，打印每个用户
//                user -> userNames.add(user.getName()) // 第二个 peek 操作，将每个用户的名字添加到 userNames 列表中
//        );
//    }


}

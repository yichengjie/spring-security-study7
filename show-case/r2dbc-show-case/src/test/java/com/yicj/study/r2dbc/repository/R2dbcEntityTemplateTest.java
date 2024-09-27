package com.yicj.study.r2dbc.repository;


import com.yicj.study.r2dbc.R2dbcApplication;
import com.yicj.study.r2dbc.entity.UserReact;
import com.yicj.study.r2dbc.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.springframework.data.relational.core.query.Criteria.where;
import static org.springframework.data.relational.core.query.Query.query;


/**
 * @author yicj
 * @since 2024/9/27
 */
@Slf4j
@SpringBootTest(classes = R2dbcApplication.class)
class R2dbcEntityTemplateTest {

    @Autowired
    private R2dbcEntityTemplate template ;

    // test save
    @Test
    void insert() throws InterruptedException {
        UserReact user = new UserReact() ;
        user.setId(CommonUtil.uuid());
        user.setName("yicj");
        user.setAccount("yicj");
        user.setPassword("123");
        user.setRole(UserReact.ROLE_USER);
        user.setInsertTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        template.insert(user).subscribe(item -> log.info("user : {}", item)); // 保存用户到数据库
        Thread.sleep(3000);

    }

    @Test
    void selectOne() throws InterruptedException {
        Mono<UserReact> loaded = template.selectOne(query(where("name").is("yicj")), UserReact.class);
        loaded.subscribe(item -> log.info("loaded user : {}", item));
        Thread.sleep(3000);
    }

    @Test
    void select() throws InterruptedException {
        Flux<UserReact> loaded = template.select(query(where("name").is("yicj")),
                UserReact.class);
        loaded.subscribe(item -> log.info("loaded user : {}", item));
        Thread.sleep(3000);
    }

    @Test
    void selectAll() throws InterruptedException {
        template.select(UserReact.class)
                .all()
                .subscribe(item -> log.info("loaded user : {}", item));
        Thread.sleep(3000);
    }

}

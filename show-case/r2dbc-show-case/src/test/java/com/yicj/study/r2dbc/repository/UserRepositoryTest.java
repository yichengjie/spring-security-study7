package com.yicj.study.r2dbc.repository;


import com.yicj.study.r2dbc.R2dbcApplication;
import com.yicj.study.r2dbc.entity.UserReact;
import com.yicj.study.r2dbc.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDateTime;

/**
 * @author yicj
 * @since 2024/9/27
 */
@Slf4j
@SpringBootTest(classes = R2dbcApplication.class)
class UserRepositoryTest {

    @Autowired
    private UserRepository repository ;

    // test save
    @Test
    void save() throws InterruptedException {
        UserReact user = new UserReact() ;
        user.setId(CommonUtil.uuid());
        user.setName("yicj");
        user.setAccount("yicj");
        user.setPassword("123");
        user.setRole(UserReact.ROLE_USER);
        user.setInsertTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        repository.save(user).subscribe(item -> log.info("user : {}", item)); // 保存用户到数据库
        Thread.sleep(3000);
    }

}

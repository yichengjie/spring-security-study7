package com.yicj.study.jpa.repository;


import com.yicj.study.jpa.HelloJapApplication;
import com.yicj.study.jpa.entity.UserReact;
import com.yicj.study.jpa.utils.CommonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDateTime;

/**
 * @author yicj
 * @since 2024/9/27
 */
@SpringBootTest(classes = HelloJapApplication.class)
class UserRepositoryTest {

    @Autowired
    private UserRepository repository ;

    // @Test save
    @Test
    void save(){
        UserReact user = new UserReact() ;
        user.setId(CommonUtil.uuid());
        user.setName("yicj");
        user.setAccount("yicj");
        user.setPassword("123");
        user.setRole(UserReact.ROLE_USER);
        user.setInsertTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        repository.save(user); // 保存用户到数据库
    }

}

package com.yicj.study.jpa.service;

import com.yicj.study.jpa.HelloJapApplication;
import com.yicj.study.jpa.entity.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * UserInfoServiceTest
 * </p>
 *
 * @author yicj
 * @since 2024/09/04 14:00
 */
@Slf4j
@SpringBootTest(classes = HelloJapApplication.class)
class UserInfoServiceTest {

    @Autowired
    private UserInfoService userInfoService ;


    @Test
    void findAll(){
        initData();
        List<UserInfo> list = userInfoService.listAll();
        log.info("list size : {}", list.size());
        list.forEach(item -> {
            System.out.println("UserInfo : " + item);
        });
    }


    @Test
    void deleteById(){
        initData();
        String id = "id1" ;
        userInfoService.deleteById(id);
    }


    private void initData(){
        for (int i = 0; i < 10; i++) {
            UserInfo userInfo = new UserInfo() ;
            userInfo.setId("id" + i);
            userInfo.setUsername("yicj" + i);
            userInfo.setPassword("123456");
            userInfo.setEmail("test" + i + "@163.com");
            userInfo.setPhone("1380013800" + i);
            userInfo.setAddress("address" + i);
            userInfo.setCreatedBy("admin");
            userInfo.setLastModifiedBy("admin");
            userInfo.setCreatedDate(LocalDateTime.now());
            userInfo.setLastModifiedDate(LocalDateTime.now());
            userInfo.setDeletedFlag(false);
            userInfoService.save(userInfo) ;
        }
    }
}

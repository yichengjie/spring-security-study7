package com.yicj.study.service;

import com.yicj.study.config.TestServiceConfiguration;
import com.yicj.study.entity.UserEntity;
import com.yicj.study.mapper.UserMapper;
import com.yicj.study.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

/**
 * <p>
 * UserServiceTest
 * </p>
 *
 * @author yicj
 * @since 2024/08/17 12:25
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestServiceConfiguration.class})
public class UserServiceTest {

    @Autowired
    private UserService userService ;

    @MockBean
    private UserMapper userMapper ;

    @Test
    void saveUser(){
        // 如果不需要返回值，可以使用下面的方式
        UserEntity entity = new UserEntity("1", "yicj", "123456", "beijing");
        int count = userService.saveUser(entity) ;
        log.info("count : {}", count);
    }

    @Disabled
    @Test
    void saveUser2(){
        given(userMapper.saveUser(any(UserEntity.class))).willReturn(99) ;
        UserEntity entity = new UserEntity("1", "yicj", "123456", "beijing");
        int count = userService.saveUser(entity) ;
        log.info("count : {}", count);
    }

    @ParameterizedTest
    @CsvSource({"1,yicj", "2,admin"})
    void testDtaFromCsv(long id, String name){
        log.info("id : {}, name : {}", id, name);
    }

    @Nested
    @DisplayName("when new")
    class WhenNew{
        @Test
        void hello(){
            log.info("hello");
        }

        @RepeatedTest(5)
        void repeat(){
            log.info("repeat");
        }
    }

}

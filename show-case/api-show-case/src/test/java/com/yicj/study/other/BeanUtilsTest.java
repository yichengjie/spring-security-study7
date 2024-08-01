package com.yicj.study.other;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;

/**
 * <p>
 * BeanUtilsTest
 * </p>
 *
 * @author yicj
 * @since 2024年08月01日 17:11
 */
@Slf4j
public class BeanUtilsTest {

    @Data
    @AllArgsConstructor
    static class UserInfo{

        private String username ;

        private String address ;

        private String email ;
    }

    @Test
    void copy(){
        UserInfo userInfo = new UserInfo("test1", "bjs", "test@qq.com") ;
        UserInfo updateValue = new UserInfo(null, "sha", null) ;

        BeanUtils.copyProperties(updateValue, userInfo);

        log.info("userInfo : {}", userInfo);
    }
}

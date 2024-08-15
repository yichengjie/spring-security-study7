package com.yicj.study.mapper;

import com.github.pagehelper.PageHelper;
import com.yicj.study.PageHelperApplication;
import com.yicj.study.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

/**
 * <p>
 * UserMapperTest
 * </p>
 *
 * @author yicj
 * @since 2024/8/15 22:37
 */
@Slf4j
@SpringBootTest(classes = PageHelperApplication.class)
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void selectAll(){
        PageHelper.startPage(1, 10);
        List<UserEntity> list = userMapper.selectAll();
        log.info("记录条数为：{}", list.size());
    }
}

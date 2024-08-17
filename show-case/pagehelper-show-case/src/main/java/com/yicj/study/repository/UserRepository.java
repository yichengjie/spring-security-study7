package com.yicj.study.repository;

import com.yicj.study.entity.UserEntity;
import com.yicj.study.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * UserRepository
 * </p>
 *
 * @author yicj
 * @since 2024/08/17 12:41
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserRepository {
    private final UserMapper userMapper ;
    public int saveUser(UserEntity userEntity) {
        log.info("real save user : {} to db !!", userEntity);
        return userMapper.saveUser(userEntity) ;
    }
}

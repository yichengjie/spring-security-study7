package com.yicj.study.service.impl;

import com.yicj.study.entity.UserEntity;
import com.yicj.study.repository.UserRepository;
import com.yicj.study.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * UserServiceImpl
 * </p>
 *
 * @author yicj
 * @since 2024/08/17 12:21
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository ;

    @Override
    public int saveUser(UserEntity entity) {
        return userRepository.saveUser(entity) ;
    }
}

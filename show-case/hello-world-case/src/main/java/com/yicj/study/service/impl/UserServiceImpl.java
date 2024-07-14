package com.yicj.study.service.impl;

import com.yicj.study.service.UserService;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * UserServiceImpl
 * </p>
 *
 * @author yicj
 * @since 2024年07月14日 9:47
 */
@Slf4j
public class UserServiceImpl implements UserService {

    @Override
    public void hello(String name) {
        log.info("hello : {}", name);
    }
}

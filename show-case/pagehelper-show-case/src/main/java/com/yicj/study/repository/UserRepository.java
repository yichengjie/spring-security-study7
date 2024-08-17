package com.yicj.study.repository;

import com.yicj.study.entity.UserEntity;

/**
 * <p>
 * UserRepository
 * </p>
 *
 * @author yicj
 * @since 2024/08/17 12:41
 */
public interface UserRepository {
    int saveUser(UserEntity userEntity) ;
}

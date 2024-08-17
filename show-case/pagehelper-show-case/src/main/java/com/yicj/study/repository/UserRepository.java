package com.yicj.study.repository;

import com.yicj.study.entity.UserEntity;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * UserRepository
 * </p>
 *
 * @author yicj
 * @since 2024/08/17 12:41
 */
public interface UserRepository {
    void saveUser(UserEntity userEntity) ;
}

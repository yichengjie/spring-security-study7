//package com.yicj.study.repository.impl;
//
//import com.yicj.study.entity.UserEntity;
//import com.yicj.study.mapper.UserMapper;
//import com.yicj.study.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Repository;
//
///**
// * <p>
// * UserRepositoryImpl
// * </p>
// *
// * @author yicj
// * @since 2024/08/17 12:42
// */
//@Slf4j
//@Repository
//@RequiredArgsConstructor
//public class UserRepositoryImpl implements UserRepository {
//
//    private final UserMapper userMapper ;
//    @Override
//    public int saveUser(UserEntity userEntity) {
//        log.info("real save user : {} to db !!", userEntity);
//        return userMapper.saveUser(userEntity) ;
//    }
//}

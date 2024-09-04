package com.yicj.study.jpa.service;


import com.yicj.study.jpa.entity.UserInfo;

import java.util.List;

/**
 * <p>
 * UserInfoService
 * </p>
 *
 * @author yicj
 * @since 2024/09/04 13:59
 */
public interface UserInfoService {

    List<UserInfo> listAll() ;

    void deleteById(String id) ;

    String save(UserInfo userInfo) ;
}

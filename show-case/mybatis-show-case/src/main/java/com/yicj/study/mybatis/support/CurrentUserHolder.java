package com.yicj.study.mybatis.support;


import com.yicj.study.mybatis.model.User;

public class CurrentUserHolder {

    private static final ThreadLocal<User> CURRENT_USER = new ThreadLocal<>();

    public static User getUser() {
       return CURRENT_USER.get() ;
    }

}

package com.yicj.study.mybatis.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.yicj.study.mybatis.entity.UserReact;

import java.util.List;

public interface UserReactService extends IService<UserReact> {

    List<UserReact> selectByName(String name) ;
}

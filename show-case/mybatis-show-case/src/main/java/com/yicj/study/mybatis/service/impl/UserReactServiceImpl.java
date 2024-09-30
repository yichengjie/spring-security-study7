package com.yicj.study.mybatis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yicj.study.mybatis.entity.UserReact;
import com.yicj.study.mybatis.mapper.UserReactMapper;
import com.yicj.study.mybatis.service.UserReactService;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class UserReactServiceImpl
        extends ServiceImpl<UserReactMapper, UserReact> implements UserReactService {

    @Override
    public List<UserReact> selectByName(String name) {
        LambdaQueryWrapper<UserReact> wrapper = new LambdaQueryWrapper<>() ;
        wrapper.eq(UserReact::getName, name) ;
        return baseMapper.selectByCustomSql(wrapper);
    }

}

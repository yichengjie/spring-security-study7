package com.yicj.study.mybatis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yicj.study.mybatis.entity.UserReact;
import com.yicj.study.mybatis.mapper.UserReactMapper;
import com.yicj.study.mybatis.service.UserReactService;
import com.yicj.study.mybatis.support.datascope.aspect.DataScope;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class UserReactServiceImpl
        extends ServiceImpl<UserReactMapper, UserReact> implements UserReactService {

    @Override
    @DataScope(userAlias = "a", userColumn = "name")
    public List<UserReact> selectByName(String name) {
        QueryWrapper<UserReact> wrapper = new QueryWrapper<>() ;
        wrapper.eq("a.name", name) ;
        return baseMapper.selectByCustomSql(wrapper);
    }

}

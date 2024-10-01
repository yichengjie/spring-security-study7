package com.yicj.study.mybatis.mapper;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.yicj.study.mybatis.entity.UserReact;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface UserReactMapper extends BaseMapper<UserReact> {

    @Select("SELECT * FROM user_react a ${ew.customSqlSegment}")
    List<UserReact> selectByCustomSql(@Param(Constants.WRAPPER) Wrapper<UserReact> wrapper);
}

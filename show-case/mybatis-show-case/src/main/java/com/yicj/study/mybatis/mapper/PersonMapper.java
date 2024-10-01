package com.yicj.study.mybatis.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yicj.study.mybatis.entity.Person;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PersonMapper extends BaseMapper<Person> {

}

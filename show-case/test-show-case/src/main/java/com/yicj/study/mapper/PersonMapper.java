package com.yicj.study.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yicj.study.entity.Person;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PersonMapper extends BaseMapper<Person> {

}

package com.yicj.study.mybatis.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yicj.study.mybatis.entity.Person;
import com.yicj.study.mybatis.mapper.PersonMapper;
import com.yicj.study.mybatis.service.PersonService;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl
        extends ServiceImpl<PersonMapper, Person> implements PersonService {

}

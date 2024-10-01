package com.yicj.study.mybatis.service;


import com.yicj.study.mybatis.HelloMybatisApplication;
import com.yicj.study.mybatis.entity.Person;
import com.yicj.study.mybatis.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@SpringBootTest(classes = HelloMybatisApplication.class)
public class PersonServiceTest {

    @Autowired
    private PersonService personService ;

    @Test
    void list() {
        Person person = new Person() ;
        person.setId(CommonUtil.uuid());
        person.setName("yicj");
        person.insert() ;
    }

}

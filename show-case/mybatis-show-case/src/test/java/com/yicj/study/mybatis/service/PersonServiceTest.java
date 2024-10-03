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
    void add(){
        Person person = new Person() ;
        person.setId(CommonUtil.uuid());
        person.setName("test");
        person.setAge(18);
        personService.save(person) ;
    }

    @Test
    void list() {
        Person person = new Person() ;
        person.setId(CommonUtil.uuid());
        person.setName("test");
        person.insert() ;
    }

    @Test
    void update(){
        Person person = new Person() ;
        person.setId("625805604c3d455389821d29262318b4");
        person.setName("test1");
        personService.updateById(person) ;
    }

}

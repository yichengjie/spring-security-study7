package com.yicj.study.mapper;

import com.yicj.study.HelloTestApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest(classes = HelloTestApplication.class)
public class PersonMapperTest {

    @Autowired
    private PersonMapper personMapper ;

    @Test
    public void testSelect(){
        System.out.println("----- selectAll method test ------");
        personMapper.selectList(null).forEach(System.out::println);
    }
}

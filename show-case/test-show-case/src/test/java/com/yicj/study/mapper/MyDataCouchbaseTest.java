package com.yicj.study.mapper;


import com.baomidou.mybatisplus.test.autoconfigure.MybatisPlusTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.couchbase.DataCouchbaseTest;
import org.springframework.boot.test.context.SpringBootTest;

//@MybatisPlusTest
@SpringBootTest
class MyDataCouchbaseTest {

    @Autowired
    private PersonMapper personMapper ;

    @Test
    void testSelect(){
        System.out.println("----- selectAll method test ------");
        personMapper.selectList(null).forEach(System.out::println);
    }
}

package com.yicj.study.mapper;

import com.yicj.study.HelloTestApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
@SpringBootTest(args = "--app.test=one")
class PersonMapperTest {

    @Autowired
    private PersonMapper personMapper ;

    @Test
    void testSelect(){
        System.out.println("----- selectAll method test ------");
        personMapper.selectList(null).forEach(System.out::println);
    }

    @Test
    void applicationArgumentsPopulated(@Autowired ApplicationArguments args){
        log.info("Application arguments: {}", args.getOptionNames());
        assertThat(args.getOptionNames()).containsOnly("app.test");
        assertThat(args.getOptionValues("app.test")).containsOnly("one");
    }

}

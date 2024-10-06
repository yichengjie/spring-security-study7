package com.yicj.study.stream;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import java.util.List;

@Slf4j
class StreamTest {

    @Test
    void count(){
        List<Integer> list = List.of(2) ;
        Integer count = list.stream()
                .reduce(0, (acc, cur) -> acc + 1, (a, b) -> {
                   int c =  a + b ;
                   log.info("a : {}, b : {}, c : {}", a, b, c);
                   return c ;
                });
        log.info("count : {}", count);
        //
        Integer count2 = list.stream()
                .reduce(0, (acc, cur) -> {
                    log.info("==[2]=> acc : {}, cur : {}", acc, cur);
                    return acc + 1;
                });
        log.info("count2 : {}", count2);
        //
        Integer count3 = list.stream()
                .reduce((acc, cur) -> {
                    log.info("==[3]=> acc : {}, cur : {}", acc, cur);
                    return acc + 1;
                })
                .orElse(null);
        log.info("count3 : {}", count3);
    }

}

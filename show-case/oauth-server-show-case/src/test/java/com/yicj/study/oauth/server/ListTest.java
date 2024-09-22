package com.yicj.study.oauth.server;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author yicj
 * @version 1.0
 * @since 2024/9/22
 */
@Slf4j
public class ListTest {

    @Test
    void hello(){
        List<String> list2 = new LinkedList<>() ;
        list2.add("hello") ;
        List<String> list = Collections.unmodifiableList(new LinkedList<>(list2));
        Assertions.assertThrows(UnsupportedOperationException.class, ()->{
            list.add("world") ;
        });
        log.info("list : {}", list);
    }
}

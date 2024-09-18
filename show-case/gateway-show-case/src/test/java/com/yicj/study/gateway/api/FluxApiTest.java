package com.yicj.study.gateway.api;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * @author yicj
 * @since 2024/9/18 21:25
 */
public class FluxApiTest {

    @Test
    void testFlatMap(){
        // flatMap方法会将每个转换后的Flux合并成一个新的Flux，并且不保证转换后的元素的顺序
        String a = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        String[] split = a.split("(?!^)");
        // print split
        Flux.just(split)
            .flatMap(s->{
                String str = s + s;
                System.out.println("flatmap to :" + str);
                return Flux.just(str);
            })
            .subscribe();
    }

    @Test
    void testConcatMap(){
        // 与flatMap不同的是，concatMap方法保证转换后的元素的顺序与原始Flux中的元素顺序一致
        String a = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        String[] split = a.split("(?!^)");
        Flux.just(split)
            .concatMap(s->{
                String str = s + s;
                System.out.println("flatmap to :" + str);
                return Flux.just(str);
            })
            .subscribe();
    }
}

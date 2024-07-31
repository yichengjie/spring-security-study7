package com.yicj.study.controller;


import com.yicj.study.service.HelloService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import java.time.Duration;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ReactorController {

    private final HelloService helloService ;

    @GetMapping("/reactor/index")
    public Flux<String> index(){
        // 当消费者断开连接之后，这里会自动停止生产数据
        return Flux.range(1,200)
                .map(item -> "value " + item)
                .delayElements(Duration.ofSeconds(2))
                .doOnNext(value -> log.info("===> {}", value));
    }


    @GetMapping("/reactor/hello")
    public Flux<String> hello(){
        return Flux.range(1,200)
                .map(item -> helloService.slow(2) + item)
                .doOnNext(value -> log.info("====> {}", value)) ;
    }

}

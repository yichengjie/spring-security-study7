package com.yicj.study.reactor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class HelloApiTest {

    @Test
    void parallel() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(100) ;
        Flux.range(1,100)
                .parallel(3)
                .runOn(Schedulers.parallel()) //parallel flu
                .map(item ->  {
                    log.info("item : {}", item);
                    sleep(5);
                    latch.countDown();
                    return "value["+item+"]" ;
                }).subscribe();
        latch.await();
    }


    @Test
    void hello(){
        LocalDate startDate = LocalDate.of(2024, 7, 1) ;
        LocalDate endDate = LocalDate.of(2024, 7, 30) ;
        Flux.generate(() -> startDate, (state, sink) ->{
            LocalDate splitDate = state.plusDays(3);
            if (state.isAfter(endDate)){
                sink.complete();
            }else {
                if (splitDate.isAfter(endDate)){
                    sink.next(new DatePair(state, endDate));
                }else {
                    sink.next(new DatePair(state, splitDate));
                }
            }
            return splitDate ;
        }).parallel(3)
        .runOn(Schedulers.parallel()) //parallel flu
        .subscribe(value -> log.info("value : {}", value)) ;
        sleep(1000);
    }

    @Data
    @AllArgsConstructor
    static class DatePair{
        private final LocalDate t1 ;
        private final LocalDate t2 ;
    }

    private void sleep(int num){
        try {
            Thread.sleep(num);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

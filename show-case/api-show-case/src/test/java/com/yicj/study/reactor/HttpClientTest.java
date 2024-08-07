package com.yicj.study.reactor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>
 * HttpClientTest
 * </p>
 *
 * @author yicj
 * @since 2024年07月28日 22:21
 */
@Slf4j
public class HttpClientTest {
    private ExecutorService pool = Executors.newFixedThreadPool(10);

    @Test
    void get() throws InterruptedException {
        String reqStr = "Go to Zibo for barbecue";
        HttpClient httpClient = HttpClient.create().port(8080);
        httpClient.get()               // Specifies that POST method will be used
                .uri("/hello/detail/1")   // Specifies the path
                //.send(ByteBufFlux.fromString(Flux.just(reqStr)))  // Sends the request body
                .responseContent()    // Receives the response body
                .aggregate()
                .asString()
                .subscribe(res -> log.info("Response: {}", res));

        Thread.sleep(2000);
    }


    @Test
    void hello(){
        Mono<String> mono1 = Mono.fromCallable(() -> {
            sleep(200);
            log.info("do something1");
            int i = 1 / 0;
            return "1" ;
        });

        Mono<Void> mono2 = Mono.fromRunnable(() -> {
            sleep(100);
            log.info("do something2");
        });

        Mono.zip(mono1, mono2).subscribe(
            value -> log.info("value: {}", value),
            error -> log.info("on error manual rollback transaction  ", error)
        ) ;
    }

    @Test
    void hello2(){
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() ->{
            sleep(200);
            log.info("do something1");
            int i = 1 / 0;
            return "1" ;
        }, pool) ;
        CompletableFuture<Void> f2 = CompletableFuture.runAsync(() -> {
            sleep(100);
            log.info("do something2");
        }, pool);
        try {
            CompletableFuture.allOf(f1, f2)
               .whenComplete((value, error) ->{
                    if (error != null){
                        log.info("on error manual rollback transaction ") ;
                    }
                }).join() ;
        }catch (Exception e){
            log.info("on error auto rollback transaction");
        }
    }

    void sleep(int num){
        try {
            Thread.sleep(num);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

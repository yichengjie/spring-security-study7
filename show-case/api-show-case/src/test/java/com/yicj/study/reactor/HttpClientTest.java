package com.yicj.study.reactor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.netty.http.client.HttpClient;

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

    @Test
    void test() throws InterruptedException {
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
}

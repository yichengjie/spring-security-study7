package com.yicj.study.gateway.filter.headers;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.lang.Nullable;
import org.springframework.web.reactive.HandlerAdapter;
import org.springframework.web.reactive.HandlerResult;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

/**
 * @author yicj
 * @since 2024/9/17 12:58
 */
@Slf4j
public class RouteToRequestUrlFilterTest {

    @Test
    void hello(){
        boolean encoded = true;
        URI uri = URI.create("http://localhost:9999");

        URI routeUri = URI.create("http://localhost:8080/hello/123");

        URI mergedUrl = UriComponentsBuilder.fromUri(uri)
            // .uri(routeUri)
            .scheme(routeUri.getScheme()).host(routeUri.getHost()).port(routeUri.getPort()).build(encoded).toUri();
        log.info("mergedUrl : {}", mergedUrl);
    }

    @Test
    void emptyError(){
        Flux.fromIterable(List.of(1, 2))
            .next()
            //.switchIfEmpty(Mono.error(new RuntimeException("error")))
            .doOnNext(item -> log.info("item : {}", item))
            .subscribe() ;
    }


    @Test
    void testOther(){
        ServerWebExchange exchange = null;
        Object handler = null;
        List<HandlerAdapter> handlerAdapters = List.of() ;
        for (HandlerAdapter adapter : handlerAdapters) {
            if (adapter.supports(handler)) {
                Mono<HandlerResult> resultMono = adapter.handle(exchange, handler);
                log.info("==========");
                return;
            }
        }
    }

    @Test
    void testOther2(){
        ServerWebExchange exchange = null;
        Object handler = null;
        List<HandlerAdapter> handlerAdapters = List.of() ;
        Flux.fromIterable(handlerAdapters)
                .filter(adapter -> adapter.supports(handler))
                .next() ;
    }

}

package com.yicj.study.gateway.filter.headers;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

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
}

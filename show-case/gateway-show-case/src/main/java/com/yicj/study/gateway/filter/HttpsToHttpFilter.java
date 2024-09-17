package com.yicj.study.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;

/**
 *
 * @author yicj
 * @since 2024/9/17 19:39
 */
public class HttpsToHttpFilter implements GlobalFilter, Ordered {

    private static final int HTTPS_SCHEME_FILTER_ORDER = 10099 ;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        URI originalUri = exchange.getRequest().getURI();
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpRequest.Builder mutate = request.mutate();
        String forwardUri = request.getURI().toString();
        if (forwardUri != null && forwardUri.startsWith("https")){
            URI newUri = this.createUriWithHttpsScheme(originalUri);
            mutate.uri(newUri);
        }
        ServerHttpRequest newRequest = mutate.build();
        return chain.filter(exchange.mutate().request(newRequest).build());
    }

    private URI createUriWithHttpsScheme(URI originalUri)  {
        try {
            return new URI(
                "http",
                originalUri.getUserInfo(),
                originalUri.getHost(),
                originalUri.getPort(),
                originalUri.getPath(),
                originalUri.getQuery(),
                originalUri.getFragment()
            ) ;
        }catch (URISyntaxException e){
            throw new IllegalArgumentException("Invalid URL: " + originalUri, e) ;
        }
    }

    @Override
    public int getOrder() {
        // before ReactiveLoadBalancerClientFilter change https -> http
        return HTTPS_SCHEME_FILTER_ORDER;
    }
}

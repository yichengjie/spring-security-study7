package com.yicj.study.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;

/**
 * @author yicj
 * @since 2024/9/17 19:55
 */
public class HttpSchemeFilter implements GlobalFilter, Ordered {

    private static final int HTTP_SCHEME_FILTER_ORDER = 10101 ;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        URI uriObj = (URI)exchange.getAttributes().get(GATEWAY_REQUEST_URL_ATTR);
        if (uriObj != null){
            URI uri = this.upgradeConnection(uriObj, "http") ;
            exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, uri) ;
        }
        return chain.filter(exchange);
    }

    private URI upgradeConnection(URI uri, String scheme) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUri(uri).scheme(scheme);
        if (uri.getRawQuery() != null){
            // When building the URI, UriComponentsBuilder verify then allowed
            // characters and does not support then "+" so we replace it for its equivalent '%20'
            // See https://jira.spring.io/browse/SPR-10172
            builder.replaceQuery(uri.getRawQuery().replace("+", "%20")); ;
        }
        return builder.build(true).toUri();
    }

    @Override
    public int getOrder() {
        // after ReactiveLoadBalancerClientFilter change https -> http
        return HTTP_SCHEME_FILTER_ORDER;
    }
}

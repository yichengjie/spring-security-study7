package com.yicj.study.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractNameValueGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @author yicj
 * @since 2024/9/17 17:16
 */
@Slf4j
@Component
public class CustomGatewayFilterFactory
        extends AbstractNameValueGatewayFilterFactory implements Ordered {
    private static final String COUNT_START_TIME = "count_start_time" ;
    @Override
    public GatewayFilter apply(NameValueConfig config) {
        return (exchange, chain) -> {
            exchange.getAttributes().put(COUNT_START_TIME, System.currentTimeMillis()) ;
            return chain.filter(exchange).then(Mono.fromRunnable(() ->{
                Long startTime = exchange.getAttribute(COUNT_START_TIME);
                if (startTime != null){
                    long endTime = System.currentTimeMillis() - startTime ;
                    log.info(exchange.getRequest().getURI().getRawPath() + ": " + endTime + "ms");
                }
            }));
        };
    }
    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE ;
    }
}

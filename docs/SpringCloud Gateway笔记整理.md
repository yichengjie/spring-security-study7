1. actuator配置, 访问http://localhost:9999/actuator/gateway/routes查看路由信息
   ```properties
   management.endpoint.gateway.enabled=true
   management.endpoints.web.exposure.include=gateway
   ```
2. logger配置
   ```properties
   logging.level.org.springframework.cloud.gateway=trace
   logging.level.org.springframework.http.server.reactive=debug
   logging.level.org.springframework.web.reactive=debug
   logging.level.reactor.ipc.netty=debug
   ```
3. 路由配置
   ```properties
   # 访问http://localhost:9999/jd时，会转发到https://www.jd.com/
   spring.cloud.gateway.routes[0].id=jd_route
   spring.cloud.gateway.routes[0].uri=https://www.jd.com/
   spring.cloud.gateway.routes[0].predicates[0]=Path=/jd
   ```
### 自定义GatewayFilter
1. 实现GatewayFilter接口
   ```java
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
   ```
2. 配置过滤器
   ```properties
   spring.cloud.gateway.routes[0].id=jd_route
   spring.cloud.gateway.routes[0].uri=https://www.jd.com/
   spring.cloud.gateway.routes[0].predicates[0]=Path=/jd
   spring.cloud.gateway.routes[0].filters[0]=Custom=name,test
   ```
### 自定义GlobalFilter
1. 实现GlobalFilter接口
   ```java
   @Component
   public class AuthSignatureFilter implements GlobalFilter, Ordered {
       @Override
       public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
           String token = exchange.getRequest().getQueryParams().getFirst("token");
           if (StringUtils.isBlank(token)){
               // 如果token为空，则返回401
               exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
               return exchange.getResponse().setComplete();
           }
           return chain.filter(exchange);
       }
       @Override
       public int getOrder() {
           return -400;
       }
   }
   ```
2. 访问：http://localhost:9999/jd?token=123正常跳转到https://www.jd.com/
### 其他
1. 执行流程
   ```text
   HttpWebHandlerAdapter
   DispatcherHandler
   RoutePredicateHandlerMapping
   FilteringWebHandler
   ```
2. 内置10个global 过滤器
    ```text
    RemoveCachedBodyFilter
    AdaptCachedBodyGlobalFilter
    NettyWriteResponseFilter
    ForwardPathFilter
    RouteToRequestUrlFilter
    ReactiveLoadBalancerClientFilter
    LoadBalancerServiceInstanceCookieFilter
    WebsocketRoutingFilter
    NettyRoutingFilter
    ForwardRoutingFilter
    ```
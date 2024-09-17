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
### 原理部分
1. 执行流程
   ```text
   HttpWebHandlerAdapter
   DispatcherHandler
   RoutePredicateHandlerMapping
   FilteringWebHandler
   ```
2. 10个global 过滤器
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
package com.yicj.study.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerRequestFactory;
import org.springframework.util.Assert;
import java.net.URI;

/**
 * @author yicj
 * @since 2024/9/15 17:33
 */
@Slf4j
class LoadBalancerRequestFactoryTest {
//
//    private LoadBalancerClient loadBalancer;
//
//    private LoadBalancerRequestFactory requestFactory;
//
//    void testCreateRequest() {
//        final URI originalUri = URI.create("http://user-service:8080/auth/login");
//        String serviceName = originalUri.getHost();
//        Assert.state(serviceName != null, "Request URI does not contain a valid hostname: " + originalUri);
//        return this.loadBalancer.execute(serviceName, this.requestFactory.createRequest(request, body, execution));
//    }

}

package com.yicj.study.controller;

import com.yicj.study.service.HelloService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MyRandomPortTestRestTemplateTest {

    @Autowired
    private TestRestTemplate restTemplate ;

    @MockBean
    private HelloService helloService ;

    @Test
    void exampleTest() {
        String body = restTemplate.getForObject("/hello/index", String.class);
        Assertions.assertThat(body).isEqualTo("Hello World");
    }

}

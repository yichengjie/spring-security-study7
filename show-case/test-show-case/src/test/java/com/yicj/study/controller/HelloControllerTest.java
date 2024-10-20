package com.yicj.study.controller;


import com.yicj.study.service.HelloService;
import com.yicj.study.service.impl.HelloServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@Slf4j
@WebMvcTest
class HelloControllerTest {

    @Autowired
    private MockMvc mvc;

//    @MockBean
//    private HelloService helloService ;

    @SpyBean
    private HelloServiceImpl helloService ;

    @Test
    void testWithMockMvc() throws Exception {
        Mockito.when(helloService.index()).thenReturn("Hello World") ;
        //
        mvc.perform(MockMvcRequestBuilders.get("/hello/index"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Hello World"));
    }

    @Test
    void testWithMockMvc2() throws Exception {
        Mockito.when(helloService.index()).thenReturn("Hello World2") ;
        //
        mvc.perform(MockMvcRequestBuilders.get("/hello/index"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Hello World2"));
    }
}

package com.yicj.elasticsearch.es.service;


import com.yicj.elasticsearch.ElasticsearchApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = ElasticsearchApplication.class)
@AutoConfigureMockMvc
public class ContentsIndexServiceTest {

    @Autowired
    private ContentsIndexService contentsIndexService ;

    @Test
    public void initIndex (){
        contentsIndexService.initIndex();
    }

}

package com.yicj.study.jpa.service;

import com.yicj.study.jpa.HelloJapApplication;
import com.yicj.study.jpa.config.YamlPropertySourceFactory;
import com.yicj.study.jpa.entity.FeatureMessage;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * FeatureMessageServiceTest
 * </p>
 *
 * @author yicj
 * @since 2024/08/18 19:25
 */
@Slf4j
//@SpringBootTest(classes = HelloJapApplication.class)
@SpringJUnitConfig
@TestPropertySource(value = "classpath:application.yml", factory = YamlPropertySourceFactory.class)
public class FeatureMessageServiceTest extends BasicServiceTest{

    @Autowired
    private FeatureMessageService featureMessageService ;

    // @Test
    @Test
    void findAll() {
        var list = featureMessageService.findAll();
        list.forEach(item -> {
            System.out.println("FeatureMessage : " + item);
        });
    }

    @Test
    void list4Page(){
        List<FeatureMessage> list = this.initFeatureMessageList(32);
        featureMessageService.batchSave(list) ;
        //
        var page = featureMessageService.list4Page(1, 10) ;
        System.out.println("page : " + page);
    }

    @Test
    void saveAll(){
        List<FeatureMessage> initList =  this.initFeatureMessageList(20) ;
        featureMessageService.batchSave(initList) ;
        //
        var list = featureMessageService.findAll();
        list.forEach(item -> {
            System.out.println("FeatureMessage : " + item);
        });
    }


    private List<FeatureMessage> initFeatureMessageList(int count){
        return Flux.range(1, count)
                .map(this::initFeatureMessage)
                .collectList().block() ;
    }

    private FeatureMessage initFeatureMessage(int index){
        return FeatureMessage.builder()
            .id("id" + index )
            .messageType("type" + index)
            .dataPermission("permission" + index)
            .messageHeadline("headline" + index)
            .summary("summary" + index)
            .messageContent("content" + index)
            .link("link" + index)
            .coverPageName("coverPageName" + index)
            .coverPageUrl("coverPageUrl" + index)
            .author("author" + index)
            .validFromDate(LocalDateTime.now())
            .validToDate(LocalDateTime.now())
            .createdBy("createdBy" + index)
            .createdDate(LocalDateTime.now())
            .lastModifiedBy("lastModifiedBy" + index)
            .lastModifiedDate(LocalDateTime.now())
            .effectiveFlag(1)
            .build() ;
    }


}

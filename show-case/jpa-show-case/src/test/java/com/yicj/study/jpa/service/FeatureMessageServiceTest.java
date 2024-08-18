package com.yicj.study.jpa.service;

import com.yicj.study.jpa.entity.FeatureMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
            .build() ;
    }


}

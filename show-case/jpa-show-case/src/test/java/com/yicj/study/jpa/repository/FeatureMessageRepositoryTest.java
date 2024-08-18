package com.yicj.study.jpa.repository;

import com.yicj.study.jpa.HelloJapApplication;
import com.yicj.study.jpa.entity.FeatureMessage;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * FeatureMessageRepositoryTest
 * </p>
 * @author yicj
 * @since 2024/08/18 15:49
 */
@Slf4j
@ActiveProfiles("dev")
@SpringBootTest(classes = HelloJapApplication.class)
public class FeatureMessageRepositoryTest {

    @Autowired
    private FeatureMessageRepository repository ;

    @Test
    void findAll() {
        var list = repository.findAll();
        list.forEach(item -> {
            log.info("FeatureMessage : {}", item);
        });
    }


    @Test
    void save() {
        var featureMessage = this.initFeatureMessage(1) ;
        repository.save(featureMessage) ;
    }

    @Test
    void saveAll(){
        List<FeatureMessage> list = Flux.range(2, 19)
                .map(this::initFeatureMessage)
                .collectList().block();
        repository.saveAll(list) ;
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

package com.yicj.study.jpa.repository;

import com.yicj.study.jpa.HelloJapApplication;
import com.yicj.study.jpa.entity.FeatureMessage;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Flux;

import java.time.Instant;
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
//@ActiveProfiles("dev")
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
        var featureMessage = this.initFeatureMessage(2) ;
        repository.save(featureMessage) ;
    }

    @Test
    void saveAll(){
        List<FeatureMessage> list = Flux.range(2, 19)
                .map(this::initFeatureMessage)
                .collectList().block();
        repository.saveAll(list) ;
    }

    @Test
    void listByStatus(){
        //Sort sort = Sort.by("LENGTH(messageContent)") ;
        Sort sort = JpaSort.unsafe(" ifnull(seq, 999999999)").descending()
                .and(Sort.by("lastModifiedDate").descending())
                .and(Sort.by("createdDate").descending()) ;
        List<FeatureMessage> list = repository.listByStatus(1, sort);
        list.forEach(item -> log.info("FeatureMessage : {}", item));
    }

    @Test
    void listByStatus2(){
        //Sort sort = Sort.by("LENGTH(messageContent)") ;
        Sort sort = Sort.by("fnSeq").descending()
                .and(Sort.by("lastModifiedDate").descending())
                .and(Sort.by("createdDate").descending()) ;
        List<Object[]> list = repository.listByStatus2(1, sort);
        list.forEach(item -> log.info("FeatureMessage : {}", item));
    }

    //

    @Test
    void list4Page(){
        //Sort sort = Sort.by("LENGTH(messageContent)") ;
        Sort sort = JpaSort.unsafe(" ifnull(seq, 999999999)").descending()
                .and(Sort.by("lastModifiedDate").descending())
                .and(Sort.by("createdDate").descending()) ;
        int pageIndex = 1 ;
        int pageSize = 10 ;
        Pageable pageable = PageRequest.of(pageIndex, pageSize, sort) ;
        String messageHeadline = null ;
        Page<FeatureMessage> pageResult = repository.list4Page(messageHeadline, pageable);
        log.info(" : {}", pageResult.getSize());
        pageResult.forEach(item -> log.info("FeatureMessage : {}", item));
    }

    @Test
    void findByMessageHeadlineOrderByLastModifiedDateDesc(){
        List<FeatureMessage> list = repository.findByMessageHeadlineOrderByLastModifiedDateDesc("headline1");
        list.forEach(item -> log.info("FeatureMessage : {}", item));
    }

    @Test
    void findTopByMessageHeadlineOrderByLastModifiedDateDesc(){
        FeatureMessage featureMessage = repository.findTopByMessageHeadlineOrderByLastModifiedDateDesc("headline1");
        log.info("FeatureMessage : {}", featureMessage);
    }

//    @Test
//    void findByMessageHeadline(){
//        String headline = "headline1" ;
//        FeatureMessage list = repository.findByMessageHeadline(headline);
//        log.info("FeatureMessage : {}", list);
//    }

    private FeatureMessage initFeatureMessage(int index){
        return FeatureMessage.builder()
                .id("id" + index )
                .messageType("type" + index)
                .messageHeadline("headline" + index)
                .summary("summary" + index)
                .messageContent("content" + index)
                .link("link" + index)
                .coverPageName("coverPageName" + index)
                .coverPageUrl("coverPageUrl" + index)
                .author("author" + index)
                .validFromDate(Instant.now())
                .validToDate(Instant.now())
                .createdBy("createdBy" + index)
                .createdDate(Instant.now())
                .lastModifiedBy("lastModifiedBy" + index)
                .lastModifiedDate(Instant.now())
                .status(1)
                .build() ;
    }
}

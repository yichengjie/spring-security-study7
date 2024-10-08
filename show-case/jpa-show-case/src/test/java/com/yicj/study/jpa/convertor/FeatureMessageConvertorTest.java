package com.yicj.study.jpa.convertor;

import com.yicj.study.jpa.dto.FeatureMessageDTO;
import com.yicj.study.jpa.entity.FeatureMessage;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;

/**
 * <p>
 * FeatureMessageConvertorTest
 * </p>
 *
 * @author yicj
 * @since 2024/8/19 22:21
 */
@Slf4j
public class FeatureMessageConvertorTest {

    @Test
    void toDTO(){
        FeatureMessage entity = this.initFeatureMessage(2) ;
        FeatureMessageDTO dto = FeatureMessageConvertor.I.toDto(entity);
        log.info("dto:{}", dto);
    }


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

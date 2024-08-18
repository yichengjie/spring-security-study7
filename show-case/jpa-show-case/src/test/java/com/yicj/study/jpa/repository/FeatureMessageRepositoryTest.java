package com.yicj.study.jpa.repository;

import com.yicj.study.jpa.HelloJapApplication;
import com.yicj.study.jpa.service.BasicServiceTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * <p>
 * FeatureMessageRepositoryTest
 * </p>
 * @author yicj
 * @since 2024/08/18 15:49
 */
@Slf4j
@ActiveProfiles("dev")
//@SpringBootTest(classes = HelloJapApplication.class)
public class FeatureMessageRepositoryTest extends BasicServiceTest {

    @Autowired
    private FeatureMessageRepository repository ;

    @Test
    void findAll() {
        var list = repository.findAll();
        list.forEach(item -> {
            log.info("FeatureMessage : {}", item);
        });
    }
}

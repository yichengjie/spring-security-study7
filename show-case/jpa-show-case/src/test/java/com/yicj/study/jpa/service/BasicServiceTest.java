package com.yicj.study.jpa.service;

import com.yicj.study.jpa.config.TestServiceConfiguration;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * <p>
 * BasicServiceTest
 * </p>
 *
 * @author yicj
 * @since 2024/08/18 19:22
 */
@ActiveProfiles("dev")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestServiceConfiguration.class})
@TestPropertySource("classpath:application.yml")
public class BasicServiceTest {

}

package com.yicj.study.jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * <p>
 * HelloJapApplication
 * </p>
 *
 * @author yicj
 * @since 2024/08/18 15:47
 */
@SpringBootApplication
public class HelloJapApplication {
    public static void main(String[] args) {
        SpringApplication.run(HelloJapApplication.class, args);
    }
}

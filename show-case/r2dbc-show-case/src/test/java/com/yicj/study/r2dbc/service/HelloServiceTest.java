package com.yicj.study.r2dbc.service;

import com.yicj.study.r2dbc.entity.Person;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import reactor.test.StepVerifier;

/**
 * @author yicj
 * @since 2024/9/11 21:52
 */
@Slf4j
public class HelloServiceTest {

    @Test
    void hello(){
        // create table
        String createTableSql = """
                CREATE TABLE person(
                   id VARCHAR(255) PRIMARY KEY,
                   name VARCHAR(255),
                   age INT
                )
                """ ;
        ConnectionFactory connectionFactory = ConnectionFactories.get("r2dbc:h2:mem:///test?options=DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
        R2dbcEntityTemplate template = new R2dbcEntityTemplate(connectionFactory) ;
        template.getDatabaseClient().sql(createTableSql)
                .fetch()
                .rowsUpdated()
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
        // insert data
        template.insert(Person.class)
                .using(Person.builder().id("1").name("Joe").age(18).build())
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
        // query data
        template.select(Person.class)
                .first()
                .doOnNext(it -> log.info("element : {}", it))
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
    }
}

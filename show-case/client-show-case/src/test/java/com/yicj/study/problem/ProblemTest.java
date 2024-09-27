package com.yicj.study.problem;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yicj.study.ClientApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zalando.problem.Problem;
import org.zalando.problem.ThrowableProblem;
import static org.zalando.problem.Status.BAD_REQUEST;

/**
 * @author yicj
 * @since 2024/9/27
 */
@Slf4j
@SpringBootTest(classes = ClientApplication.class)
class ProblemTest {

    @Autowired
    private ObjectMapper objectMapper ;

    @Test
    void hello(){
        Problem problem = Problem.builder()
                .withType(URI.create("https://example.com/probs/out-of-credit"))
                .withTitle("You do not have enough credit.")
                .withStatus(BAD_REQUEST)
                .withDetail("Your current balance is 30, but that costs 50.")
                .with("balance", 30)
                .build();
        log.info("problem : {}", problem);

        Problem.builder()
                .withType(URI.create("https://example.org/out-of-stock"))
                .withTitle("Out of Stock")
                .withStatus(BAD_REQUEST)
                .withDetail("Item B00027Y5QG is no longer available")
                .with("product", "B00027Y5QG")
                .build();
    }



    @Test
    void hello2() throws JsonProcessingException {
        ThrowableProblem problem = Problem.builder()
            .withType(URI.create("https://example.org/order-failed"))
            .withTitle("Order failed")
            .withStatus(BAD_REQUEST)
            .withCause(Problem.builder()
                .withType(URI.create("https://example.org/out-of-stock"))
                .withTitle("Out of Stock")
                .withStatus(BAD_REQUEST)
                .build())
            .build();
        ThrowableProblem cause = problem.getCause();// standard API of java.lang.Throwable
        log.info("cause : {}", cause.getMessage());
        String value = objectMapper.writeValueAsString(problem);
        log.info("problem : {}", value);
    }
}

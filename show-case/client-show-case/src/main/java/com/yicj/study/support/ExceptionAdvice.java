package com.yicj.study.support;

import com.yicj.study.exception.OutOfCreditException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import java.net.URI;


/**
 * @author yicj
 * @since 2024/9/27
 */
@RestControllerAdvice
public class ExceptionAdvice{

    @ExceptionHandler(OutOfCreditException.class)
    public ResponseEntity<Problem> handleOutOfCredit(OutOfCreditException ex) {
        Problem problem = Problem.builder()
                .withType(URI.create("https://example.com/probs/out-of-credit"))
                .withTitle("You do not have enough credit.")
                .withStatus(Status.BAD_REQUEST)
                .withDetail(ex.getMessage())
                .with("balance", ex.getBalance())
                .build();

        return ResponseEntity.status(Status.BAD_REQUEST.getStatusCode()).body(problem);
    }
}

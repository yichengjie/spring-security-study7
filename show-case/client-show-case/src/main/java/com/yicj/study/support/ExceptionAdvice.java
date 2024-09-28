package com.yicj.study.support;

import com.yicj.study.constants.ErrorConstants;
import com.yicj.study.constants.ErrorEnum;
import com.yicj.study.exception.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.StatusType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Problem> handleValidateError(MethodArgumentNotValidException ex) {
        String errorMessage = Mono.justOrEmpty(ex.getBindingResult())
            .map(Errors::getAllErrors)
            .flatMapMany(Flux::fromIterable)
            .map(item -> item.getDefaultMessage() == null ? "" : item.getDefaultMessage())
            .filter(StringUtils::isNotBlank)
            .collectList()
            .map(list -> String.join(", ", list))
            .block();
        Problem problem = Problem.builder()
                .withType(ErrorConstants.DEFAULT_TYPE)
                .withTitle("Validation failed")
                .withStatus(Status.BAD_REQUEST)
                .withDetail(errorMessage)
                .build();
        return ResponseEntity.status(Status.BAD_REQUEST.getStatusCode()).body(problem);
    }

    @ExceptionHandler({
        NotFoundException.class,
        ServiceUnavailableException.class,
        BadRequestAlertException.class,
        PermissionDeniedException.class
    })
    public ResponseEntity<Problem> handleNotFoundError(AbstractThrowableProblem ex) {
        StatusType status = ex.getStatus();
        Problem problem = Problem.builder()
                .withType(ex.getType())
                .withTitle(ex.getMessage())
                .withStatus(status)
                .withDetail(ex.getMessage())
                .build();
        return ResponseEntity.status(status.getStatusCode()).body(problem);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Problem> handleInternalError(Exception ex) {
        ErrorEnum error = ErrorEnum.INTERNAL_SERVER_ERROR;
        Problem problem = Problem.builder()
                .withType(ErrorConstants.DEFAULT_TYPE)
                .withTitle(error.getMessage())
                .withStatus(Status.INTERNAL_SERVER_ERROR)
                .withDetail(ex.getMessage())
                .build();
        return ResponseEntity.status(Status.INTERNAL_SERVER_ERROR.getStatusCode()).body(problem);
    }
}

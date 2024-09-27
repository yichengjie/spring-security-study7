package com.yicj.study.r2dbc.exception;

import com.yicj.study.r2dbc.enums.Code;
import com.yicj.study.r2dbc.model.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.r2dbc.UncategorizedR2dbcException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;
 
@Slf4j
@RestControllerAdvice
public class ExceptionController {
    // 处理XException异常，用于Mono中异常的处理
    // 注意：此方法在filter内无效，需要在单独处理。
    @ExceptionHandler(XException.class)
    public Mono<ResultVO<String>> handleXException(XException exception) {
        // 如果异常中有错误码，则返回带有错误码的错误信息
        if(exception.getCode() != null) {
            return Mono.just(ResultVO.error(exception.getCode()));
        }
        // 否则，返回带有错误码和错误信息的默认错误信息
        return Mono.just(ResultVO.error(exception.getCodeN(), exception.getMessage()));
    }
 
    // 处理通用的Exception异常
    @ExceptionHandler(Exception.class)
    public Mono<ResultVO<String>> handleException(Exception exception) {
        // 返回带有BAD_REQUEST错误码和异常信息的错误信息
        return Mono.just(ResultVO.error(Code.BAD_REQUEST.getCode(), exception.getMessage()));
    }
 
    // 处理UncategorizedR2dbcException异常，通常与数据库操作相关
    @ExceptionHandler(UncategorizedR2dbcException.class)
    public Mono<ResultVO<String>> handelUncategorizedR2dbcException(UncategorizedR2dbcException exception) {
        // 返回带有BAD_REQUEST错误码和"唯一约束冲突！"加上异常信息的错误信息
        return Mono.just(ResultVO.error(Code.BAD_REQUEST.getCode(), "唯一约束冲突！" + exception.getMessage()));
    }
}
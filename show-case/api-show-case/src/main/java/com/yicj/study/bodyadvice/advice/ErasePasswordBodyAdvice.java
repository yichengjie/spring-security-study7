package com.yicj.study.bodyadvice.advice;

import com.yicj.study.bodyadvice.anno.ErasePasswordAnno;
import com.yicj.study.model.BasicUser;
import com.yicj.study.model.ResultEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import java.lang.reflect.Method;
import java.util.*;

@Slf4j
@ControllerAdvice
public class ErasePasswordBodyAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 如果返回结果
        Method method = returnType.getMethod();
        if (method == null){
            return false;
        }
        // 获取类中是否有 ErasePasswordAnno 注解
        boolean presentController = method.isAnnotationPresent(ErasePasswordAnno.class);
        // 获取类中是否有RestController注解
        boolean restController = returnType.getDeclaringClass().isAnnotationPresent(RestController.class);
        // 支持修改结果
        boolean returnTypeSupport = ResultEntity.class.isAssignableFrom(returnType.getParameterType());
        return restController && presentController && returnTypeSupport;
    }

    @Override
    public Object beforeBodyWrite(
            Object body, MethodParameter returnType,
            MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType,
            ServerHttpRequest request,
            ServerHttpResponse response) {
        if (body == null){
            return null;
        }
        Method method = returnType.getMethod();
        if (method == null){
            return body;
        }
        Object data = ((ResultEntity<?>) body).getData();
        if (data == null){
            return body;
        }
        Object value = Optional.ofNullable(this.getSpelExpression(method))
                .map(expression -> {
                    EvaluationContext context = new StandardEvaluationContext(data);
                    ExpressionParser parser = new SpelExpressionParser();
                    return parser.parseExpression(expression).getValue(context, Object.class);
                })
                .orElse(data);
        this.erasePassword(value);
        return body ;
    }

    private String getSpelExpression(Method method){
        return Optional.ofNullable(AnnotationUtils.getAnnotation(method, ErasePasswordAnno.class))
                .map(ErasePasswordAnno::expression)
                .filter(StringUtils::isNotBlank)
                .map(String::trim)
                .orElse(null);
    }

    private void erasePassword(Object data){
        if (data instanceof Collection<?>){
            for (Object item : (Iterable<?>) data){
                this.doErasePassword(item);
            }
        }else {
            doErasePassword(data);
        }
    }


    private void doErasePassword(Object data){
        if (data instanceof BasicUser){
            ((BasicUser) data).setPassword("******");
        }
        //todo 其他类型的数据处理
    }
}
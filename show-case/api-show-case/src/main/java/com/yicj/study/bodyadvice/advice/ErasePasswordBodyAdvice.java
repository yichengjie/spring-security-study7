package com.yicj.study.bodyadvice.advice;

import com.yicj.study.bodyadvice.anno.ErasePasswordAnno;
import com.yicj.study.bodyadvice.anno.MapErasePasswordAnno;
import com.yicj.study.bodyadvice.extractor.ValueExtractorManager;
import com.yicj.study.model.BasicUser;
import com.yicj.study.model.ResultEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
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
        boolean presentController = method.isAnnotationPresent(ErasePasswordAnno.class) || method.isAnnotationPresent(MapErasePasswordAnno.class);
        // 获取类中是否有RestController注解
        boolean restController = returnType.getDeclaringClass().isAnnotationPresent(RestController.class);
        // 支持修改结果
        boolean returnTypeSupport = ResultEntity.class.isAssignableFrom(returnType.getParameterType());
        return restController && presentController && returnTypeSupport;
    }

    @Override
    public Object beforeBodyWrite(
            Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
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
        Object value = Optional.ofNullable(this.getExtractorName(method))
                .filter(StringUtils::isNotBlank)
                .map(String::trim)
                .map(name -> ValueExtractorManager.extract(data, name))
                .orElse(data);
        this.erasePassword(value);
        return body ;
    }

    private String getExtractorName(Method method){
        return Optional.ofNullable(method.getAnnotation(ErasePasswordAnno.class))
                .map(ErasePasswordAnno::value)
                .filter(StringUtils::isNotBlank)
                .map(String::trim)
                .orElseGet(()->{
                    MapErasePasswordAnno pageErasePasswordAnno = method.getAnnotation(MapErasePasswordAnno.class);
                    return pageErasePasswordAnno != null ? pageErasePasswordAnno.value() : null ;
                });
    }

    private void erasePassword(Object data){
        if (data instanceof Iterator<?>){
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
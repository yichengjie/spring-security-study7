package com.yicj.study.bodyadvice.advice;

import com.yicj.study.bodyadvice.anno.ErasePasswordAnno;
import com.yicj.study.bodyadvice.model.BasicUser;
import com.yicj.study.bodyadvice.model.ResultEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class ErasePasswordBodyAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 如果返回结果
        Method method = returnType.getMethod();
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
            Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        Object data = ((ResultEntity<?>) body).getData();
        if (data == null){
            return body;
        }
        this.erasePassword(data);
        return body ;
    }

    private void erasePassword(Object data){
        if (data instanceof List<?>){
            for (Object item : (List<?>) data){
                this.erasePassword(item);
            }
        } else if (data instanceof Map<?,?>){
            Map<?,?> map = (Map<?,?>) data;
            for (Object value : map.values()){
                this.erasePassword(value);
            }
        }else {
            doErasePassword(data);
        }
    }


    private void doErasePassword(Object data){
        if (data instanceof BasicUser){
            BasicUser basicUser = (BasicUser) data;
            basicUser.setPassword("******");
        }
        //todo 其他类型的数据处理
    }

}
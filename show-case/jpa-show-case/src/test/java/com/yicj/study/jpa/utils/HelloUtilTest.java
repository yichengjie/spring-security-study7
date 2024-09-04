package com.yicj.study.jpa.utils;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.yicj.study.jpa.entity.BaseEntity;
import com.yicj.study.jpa.entity.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.property.PropertyNamer;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.util.function.Function;

/**
 * <p>
 * HelloUtilTest
 * </p>
 *
 * @author yicj
 * @since 2024/9/4 22:01
 */
@Slf4j
class HelloUtilTest {

    @Test
    void hello() throws Exception {
        SFunction<UserInfo, Boolean> func = UserInfo::getDeletedFlag;
        Method method = func.getClass().getDeclaredMethod("writeReplace");
        method.setAccessible(true);
        Object invoke = method.invoke(func);
        log.info(invoke.toString()) ;
    }

    @Test
    void hello2() throws Exception {
        SFunc<UserInfo, Boolean> func = UserInfo::getDeletedFlag;
        Method method = func.getClass().getDeclaredMethod("writeReplace");
        method.setAccessible(true);
        SerializedLambda lambda = (SerializedLambda)method.invoke(func);
        String implMethodName = lambda.getImplMethodName();
        log.info("implMethodName : {}", implMethodName) ;
        String fieldName = PropertyNamer.methodToProperty(implMethodName);
    }

    interface SFunc<T,R> extends Function<T, R>, Serializable {

    }
}

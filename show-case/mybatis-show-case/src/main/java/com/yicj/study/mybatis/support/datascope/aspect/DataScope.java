package com.yicj.study.mybatis.support.datascope.aspect;

import java.lang.annotation.*;
 
/**
 * 数据权限过滤注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataScope {
 
    /**
     * 用户表的别名
     */
    String userAlias() default "";
 
    /**
     * 用户字段名
     */
    String userColumn() default "";
 
    /**
     * 店铺表的别名
     */
    String shopAlias() default "";
 
    /**
     * 店铺字段名
     */
    String shopColumn() default "";
 
 
}
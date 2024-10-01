package com.yicj.study.mybatis.support.datascope.holder;

import com.yicj.study.mybatis.support.datascope.aspect.DataScope;

/**
 * DataScope上下文对象
 */
 
public abstract class AbstractDataScopeContextHolder {
    private static final ThreadLocal<DataScope> CONTEXT = new InheritableThreadLocal<>();
 
    public static void set(DataScope dataScope) {
        CONTEXT.set(dataScope);
    }
 
    public static DataScope get() {
        return CONTEXT.get();
    }
 
    public static void remove() {
        CONTEXT.remove();
    }
 
}
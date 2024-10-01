package com.yicj.study.mybatis.support.datascope.aspect;

import com.yicj.study.mybatis.support.datascope.holder.AbstractDataScopeContextHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
 
/**
 * 数据权限切面
 */
@Aspect
@Component
public class DataScopeAspect {
 
    @Before("@annotation(datascope)")
    public void doBefore(JoinPoint point, DataScope datascope) throws Throwable {
        resetContextHolders();
        initContextHolders(datascope);
    }
 
    protected void initContextHolders(DataScope dataScope) {
        AbstractDataScopeContextHolder.set(dataScope);
    }
 
    private void resetContextHolders() {
        AbstractDataScopeContextHolder.remove();
    }
 
}
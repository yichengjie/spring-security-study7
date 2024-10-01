package com.yicj.study.mybatis.support.datascope.handler;

import com.yicj.study.mybatis.support.datascope.aspect.DataScope;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.statement.select.PlainSelect;
 
/**
 * 数据权限处理接口
 */
public interface DataScopeHandler {
 
    /**
     * 处理数据权限sql
     * @param plainSelect sql解析器
     * @param dataScope   数据范围注解
     * @throws JSQLParserException SQL解析异常
     */
    void handlerDataScopeSql(PlainSelect plainSelect, DataScope dataScope) throws JSQLParserException;
 
}
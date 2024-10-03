package com.yicj.study.mybatis.support.datascope.interceptor;

import com.baomidou.mybatisplus.core.plugins.InterceptorIgnoreHelper;
import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.yicj.study.mybatis.model.User;
import com.yicj.study.mybatis.support.CurrentUserHolder;
import com.yicj.study.mybatis.support.datascope.aspect.DataScope;
import com.yicj.study.mybatis.support.datascope.handler.DataScopeHandler;
import com.yicj.study.mybatis.support.datascope.holder.AbstractDataScopeContextHolder;
import lombok.extern.log4j.Log4j2;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import java.util.List;
 
/**
 * Mybatis-Plus数据权限拦截器插件
 */
@Log4j2
public class DataScopeInnerInterceptor implements InnerInterceptor {
 
    private final DataScopeHandler dataScopeHandler;
 
    public DataScopeInnerInterceptor(DataScopeHandler dataScopeHandler) {
        this.dataScopeHandler = dataScopeHandler;
    }
 
    @Override
    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) {
        // 获取登录用户，或获取其他条件，不执行数据权限  todo
        User user = CurrentUserHolder.getUser();
        if (null == user) {
            if (log.isInfoEnabled()) {
                log.info("未登录无user不执行数据权限");
            }
            return;
        }
        if (BooleanUtils.isTrue(user.getAdmin())) {
            if (log.isInfoEnabled()) {
                log.info("管理员不执行数据权限");
            }
            return;
        }
        // todo ........
        if (InterceptorIgnoreHelper.willIgnoreTenantLine(ms.getId())) {
            return;
        }
        PluginUtils.MPBoundSql mpBs = PluginUtils.mpBoundSql(boundSql);
        // 原始SQL
        String originalSql = mpBs.sql();
        if (log.isInfoEnabled()) {
            log.warn("Original SQL: {}", originalSql);
        }
        try {
            Statement statement = CCJSqlParserUtil.parse(originalSql);
            if (statement instanceof Select) {
                Select select = (Select) statement;
                // 解析SQL
                this.processSelect(select);
                final String parserSql = statement.toString();
                mpBs.sql(parserSql);
                if (log.isInfoEnabled()) {
                    log.warn("parser SQL: {}", parserSql);
                }
            }
        } catch (JSQLParserException e) {
            log.error("Failed to process, Error SQL: {}", originalSql, e);
            throw ExceptionUtils.mpe("Failed to process, Error SQL: %s", e, originalSql);
        }
    }


    /**
     * 解析sql
     */
    protected void processSelect(Select select) {
        // 处理sqlBody
        this.processSelectBody(select.getSelectBody());
        List<WithItem> withItemsList = select.getWithItemsList();
        if (!CollectionUtils.isEmpty(withItemsList)) {
            withItemsList.forEach(this::processSelectBody);
        }
    }
 
    /**
     * 处理sqlBody
     */
    protected void processSelectBody(Select selectBody) {
        if (selectBody == null) {
            return;
        }
        if (selectBody instanceof PlainSelect) {
            // 处理 PlainSelect
            this.processPlainSelect((PlainSelect) selectBody);
        } else if (selectBody instanceof WithItem) {
            // With关键字
            WithItem withItem = (WithItem) selectBody;
            // jsqlparser 4.3版本 使用 {@code withItem.getSubSelect().getSelectBody())} 代替 {@code withItem.getSelectBody()}
            processSelectBody(withItem.getSelect().getSelectBody());
        } else {
            // 集合操作 UNION(并集) MINUS(差集)
            SetOperationList operationList = (SetOperationList) selectBody;
            List<Select> selectBodyList = operationList.getSelects();
            if (CollectionUtils.isNotEmpty(selectBodyList)) {
                selectBodyList.forEach(this::processSelectBody);
            }
        }
    }
 
    /**
     * 处理 PlainSelect
     */
    protected void processPlainSelect(PlainSelect plainSelect) {
        DataScope dataScope = AbstractDataScopeContextHolder.get();
        if (dataScope != null) {
            try {
                dataScopeHandler.handlerDataScopeSql(plainSelect, dataScope);
            } catch (JSQLParserException e) {
                throw ExceptionUtils.mpe("Failed to process, Error SQL: %s", e);
            }
        }
    }

}
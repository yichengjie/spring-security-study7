package com.yicj.study.mybatis.support;


import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.handler.MultiDataPermissionHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.DataPermissionInterceptor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
class MultiDataPermissionInterceptor3Test {

    private static final List<DataScope> dataScopes = new ArrayList<>(){
        {
            add(new SysUserDataScope()) ;
            add(new SysUserRoleDataScope()) ;
        }
    } ;

    private static final DataPermissionInterceptor interceptor = new DataPermissionInterceptor(new MultiDataPermissionHandler() {

        @Override
        public Expression getSqlSegment(final Table table, final Expression where, final String mappedStatementId) {
            return dataScopes.stream()
                    .map(ds -> ds.getSqlSegment(table.getName(), table.getAlias()))
                    .filter(Objects::nonNull)
                    .map(sql -> this.parseExpression(sql, table, mappedStatementId))
                    .reduce(AndExpression::new)
                    .orElse(null);
        }

        private Expression parseExpression(String sql, Table table, String mappedStatementId) {
            if (table.getAlias() != null) {
                // 替换表别名
                sql = sql.replaceAll("u\\.", table.getAlias().getName() + StringPool.DOT);
            }
            return this.doParseExpression(sql, table, mappedStatementId);
        }

        private Expression doParseExpression(String sql, Table table, String mappedStatementId) {
            Expression sqlSegmentExpression = null;
            try {
                sqlSegmentExpression = CCJSqlParserUtil.parseCondExpression(sql);
            } catch (JSQLParserException e) {
                throw new RuntimeException(e);
            }
            log.info("{} {} AS {} : {}", mappedStatementId, table.getName(), table.getAlias(), sqlSegmentExpression.toString());
            return sqlSegmentExpression;
        }

        private Expression merge(Expression where, Expression expression){
            if (where == null){
                return expression ;
            }
            return new AndExpression(where, expression) ;
        }
    });



    @Test
    void hello(){
        String sql = """
                select u.username 
                from sys_user u 
                    join sys_user_role r on u.id=r.user_id where r.role_id=3
                """ ;
        String retSql = interceptor.parserSingle(sql, "selectByUsername");
        log.info("retSql : {}", retSql);
        // SELECT u.username
        // FROM sys_user u
        //    JOIN sys_user_role r
        //      ON u.id = r.user_id
        //      AND r.role_id = 3
        //      AND r.role_id IN (7, 9, 11)
        // WHERE r.role_id = 3
        // AND (u.username = '123' OR u.userId IN ('1', '2', '3'))
    }


    @Test
    void world() throws JSQLParserException {
        //  u.username = '123' OR u.userId IN '1', '2', '3'
        String sqlSegment = "( u.username = '123' OR u.userId IN ('1', '2', '3') )" ;
        Expression expression = CCJSqlParserUtil.parseCondExpression(sqlSegment);
        log.info("expression : {}", expression);
    }


    interface DataScope{

        boolean support(String tableName, Alias tableAlias) ;

        String getSqlSegment(String tableName, Alias tableAlias) ;
    }

    static class SysUserDataScope implements DataScope{

        @Override
        public boolean support(String tableName, Alias tableAlias) {
            return "sys_user".equalsIgnoreCase(tableName);
        }

        @Override
        public String getSqlSegment(String tableName, Alias alias) {
            if (!this.support(tableName, alias)){
                return null ;
            }
            // (u.username = '123' or u.userId in ('1', '2', '3'))
            return "(u.username = '123' or u.userId in ('1','2','3'))" ;
        }
    }

    static class SysUserRoleDataScope implements DataScope{

        @Override
        public boolean support(String tableName, Alias tableAlias) {
            return "sys_user_role".equalsIgnoreCase(tableName);
        }

        @Override
        public String getSqlSegment(String tableName, Alias alias) {
            if (!this.support(tableName, alias)){
                return null ;
            }
            // "r.role_id=3 AND r.role_id IN (7,9,11)"
            return "r.role_id=3 AND r.role_id IN (7,9,11)" ;
        }

    }

}

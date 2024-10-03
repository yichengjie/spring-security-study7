package com.yicj.study.mybatis.support;


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
class MultiDataPermissionInterceptor2Test {


    private static List<DataScope> dataScopes = new ArrayList<>(){
        {
            add(new SysUserDataScope()) ;
            add(new SysUserRoleDataScope()) ;
        }
    } ;


    private static final DataPermissionInterceptor interceptor = new DataPermissionInterceptor(new MultiDataPermissionHandler() {

        @Override
        public Expression getSqlSegment(final Table table, final Expression where, final String mappedStatementId) {
            return dataScopes.stream()
                    .filter(ds -> ds.support(table.getName(), table.getAlias()))
                    .map(ds -> ds.getExpression(table.getName(), table.getAlias()))
                    .filter(Objects::nonNull)
                    .reduce(AndExpression::new)
                    .orElse(null);
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
        // select u.username
        // from sys_user u
        //   join sys_user_role r on
        //	 u.id = r.user_id
        //	 and r.role_id = '3'
        //	 and r.role_id in ('7', '9', '11')
        // where
        //	 r.role_id = 3
        //	 and (u.username = '123' or u.userId in ('1', '2', '3'))
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

        Expression getExpression(String tableName, Alias tableAlias) ;
    }

    static class SysUserDataScope implements DataScope{

        @Override
        public boolean support(String tableName, Alias tableAlias) {
            return "sys_user".equalsIgnoreCase(tableName);
        }

        @Override
        public Expression getExpression(String tableName, Alias alias) {
            EqualsTo username = Util.equalsTo( alias,"username", "123") ;
            InExpression userId = Util.inExpression(alias, "userId", List.of("1","2","3")) ;
            return new Parenthesis(new OrExpression(username, userId)) ;
        }
    }

    static class SysUserRoleDataScope implements DataScope{

        @Override
        public boolean support(String tableName, Alias tableAlias) {
            return "sys_user_role".equalsIgnoreCase(tableName);
        }

        @Override
        public Expression getExpression(String tableName, Alias alias) {
            // "r.role_id=3 AND r.role_id IN (7,9,11)"
            EqualsTo roleId = Util.equalsTo(alias, "role_id", "3") ;
            InExpression roleIds = Util.inExpression(alias, "role_id", List.of("7","9","11")) ;
            return new AndExpression(roleId, roleIds) ;
        }

    }


    static class Util{
        public static InExpression inExpression(Alias tableAlias, String columnName, List<String> values){
            List<StringValue> list = values.stream().map(StringValue::new).toList();
            return new InExpression(column(tableAlias, columnName), new Parenthesis(new ExpressionList<>(list))) ;
        }

        public static EqualsTo equalsTo(Alias tableAlias, String columnName, String value){
            return new EqualsTo(column(tableAlias, columnName), new StringValue(value)) ;
        }

        private static Column column(Alias tableAlias, String columnName){
            String columnNUm =  tableAlias == null ? columnName : tableAlias.getName() + "." + columnName ;
            return new Column(columnNUm) ;
        }
    }
}

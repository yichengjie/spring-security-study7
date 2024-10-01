package com.yicj.study.mybatis.support.datascope.handler;


import cn.hutool.core.util.StrUtil;
import com.yicj.study.mybatis.model.User;
import com.yicj.study.mybatis.support.CurrentUserHolder;
import com.yicj.study.mybatis.support.datascope.aspect.DataScope;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.PlainSelect;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class DefaultDataScopeHandler implements DataScopeHandler{

    /**
     * 处理数据权限sql
     * @param plainSelect sql解析器
     * @param dataScope   数据范围注解
     */
    @Override
    public void handlerDataScopeSql(PlainSelect plainSelect, DataScope dataScope) throws JSQLParserException {
        // todo 从登录用户中获取数据权限
        User user = CurrentUserHolder.getUser();
        // 1、仅限本人查看数据权限
        String userColumn = dataScope.userColumn();
        if (StrUtil.isNotEmpty(userColumn)) {
            String userAlias = dataScope.userAlias();
            String column;
            if (StrUtil.isEmpty(userAlias)) {
                column = String.format("%s", userColumn);
            } else {
                column = String.format("%s.%s", userAlias, userColumn);
            }
            EqualsTo expression = new EqualsTo();
            expression.setLeftExpression(new Column(column));
            expression.setRightExpression(new StringValue(user.getUserName()));
            this.setWhere(plainSelect, expression);
        }
        // 2、店铺权限
        String shopColumn = dataScope.shopColumn();
        if (StrUtil.isNotEmpty(shopColumn)) {
            String shopAlias = dataScope.shopAlias();
            String column;
            if (StrUtil.isEmpty(shopAlias)) {
                column = String.format("%s", shopColumn);
            } else {
                column = String.format("%s.%s", shopAlias, shopColumn);
            }
            // 数据权限数据组装in条件
            List<String> shops = user.getShops();
            // 把集合转变为JSQLParser需要的元素列表
            ExpressionList itemsList = new ExpressionList(shops.stream().map(StringValue::new).collect(Collectors.toList()));
            // 创建in表达式对象，传入列名及in范围列表
            InExpression inExpression = new InExpression(new Column(column), itemsList);
            this.setWhere(plainSelect, inExpression);
        }
        // 3、其他数据权限处理where条件 todo
    }

    /**
     * 拼接到where条件
     */
    private void setWhere(PlainSelect plainSelect, Expression expression) {
        Expression where = plainSelect.getWhere();
        if (where == null) {
            // 不存在 where 条件
            plainSelect.setWhere(new Parenthesis(expression));
        } else {
            // 存在 where 条件 and 处理
            plainSelect.setWhere(new AndExpression(plainSelect.getWhere(), expression));
        }
    }
}

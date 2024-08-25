package com.yicj.study.mybatis.utils;

import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import org.junit.jupiter.api.Test;

/**
 * <p>
 * CCJSqlParserUtilTest
 * </p>
 *
 * @author yicj
 * @since 2024/08/25 11:54
 */
@Slf4j
public class CCJSqlParserUtilTest {

    @Test
    void parse() throws Exception{
        String sql = "SELECT * FROM user WHERE status = 'active'";
        PlainSelect select = (PlainSelect) ((Select) CCJSqlParserUtil.parse(sql)).getSelectBody();
        System.out.println(select); // 输出：SELECT * FROM user WHERE status = 'active'
        Expression expression = CCJSqlParserUtil.parseCondExpression("status = 'inactive'");
        select.setWhere(expression);
        System.out.println(select); // 输出：SELECT * FROM user WHERE status = 'inactive'
    }
}

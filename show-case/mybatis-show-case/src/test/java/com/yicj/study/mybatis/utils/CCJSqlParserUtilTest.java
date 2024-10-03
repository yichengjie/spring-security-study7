package com.yicj.study.mybatis.utils;

import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.WithItem;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

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

    // https://blog.csdn.net/lsygood/article/details/138216759
    @Test
    void parse() throws Exception{
        String sql = "SELECT * FROM user WHERE status = 'active'";
        PlainSelect select = (PlainSelect) ((Select) CCJSqlParserUtil.parse(sql)).getSelectBody();
        System.out.println(select); // 输出：SELECT * FROM user WHERE status = 'active'
        Expression expression = CCJSqlParserUtil.parseCondExpression("status = 'inactive'");
        select.setWhere(expression);
        System.out.println(select); // 输出：SELECT * FROM user WHERE status = 'inactive'
    }

    @Nested
    class SelectBodyTest{

        @ParameterizedTest
        @ValueSource(strings = {
            "SELECT t1.a , t1.b  FROM tab1 AS t1 JOIN tab2 t2 ON t1.user_id  = t2.user_id",
            "SELECT t1.a , t1.b  FROM tab1 AS t1, tab2 t2 WHERE t1.user_id = t2.user_id and t1.user_id = 1"
        })
        void selectBody(String sql) throws JSQLParserException {
            Select select = (Select) CCJSqlParserUtil.parse(sql);
            // select body
            Select selectBody = select.getSelectBody();
            log.info("selectBody: {}", selectBody);
            // with items
            List<WithItem> withItemsList = select.getWithItemsList();
            if (withItemsList != null) {
                for (WithItem withItem : withItemsList) {
                    log.info("withItem: {}", withItem);
                }
            }
        }
    }


    @Nested
    class WithItemsTest{
        @Test
        void withItems() throws JSQLParserException {
            String SQL003="WITH t AS ( SELECT * FROM user WHERE user.user_name = 'test' ) SELECT t.* FROM t";
            Select select = (Select) CCJSqlParserUtil.parse(SQL003);
            //
            Select selectBody = select.getSelectBody();
            log.info("selectBody: {}", selectBody);
            // with items
            List<WithItem> withItemsList = select.getWithItemsList();
            for (WithItem withItem : withItemsList) {
                log.info("withItem: {}", withItem);
            }

        }
    }


}

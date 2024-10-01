package com.yicj.study.mybatis.utils;


import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.util.TablesNamesFinder;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.util.Iterator;
import java.util.List;

@Slf4j
class CCJSqlParserManagerTest {

    @Test
    void parse() throws JSQLParserException {
        CCJSqlParserManager pm = new CCJSqlParserManager();
        String sql = "SELECT * " +
                " FROM MY_TABLE1, MY_TABLE2, (SELECT * FROM MY_TABLE3) " +
                " LEFT OUTER JOIN MY_TABLE4 "+
                " WHERE ID = (SELECT MAX(ID) FROM MY_TABLE5) " +
                " AND ID2 IN (SELECT * FROM MY_TABLE6)" ;
        Statement statement = pm.parse(new StringReader(sql));
        //-------------------------------------------//
        Select selectStatement = (Select) statement;
        TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
        List<String> tableList = tablesNamesFinder.getTableList(selectStatement);
        for (Iterator<String> iter = tableList.iterator(); iter.hasNext();) {
            System.out.println("===> table name : " + iter.next());
        }
    }
    //
    @Test
    void parse2() throws JSQLParserException {
        CCJSqlParserManager pm = new CCJSqlParserManager();
        String sql = "SELECT * " +
                " FROM MY_TABLE1, MY_TABLE2, (SELECT * FROM MY_TABLE3) " +
                " LEFT OUTER JOIN MY_TABLE4 "+
                " WHERE ID = (SELECT MAX(ID) FROM MY_TABLE5) " +
                " AND ID2 IN (SELECT * FROM MY_TABLE6)" ;
        Statement statement = CCJSqlParserUtil.parse(sql);
        //-------------------------------------------//
        Select selectStatement = (Select) statement;
        TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
        List<String> tableList = tablesNamesFinder.getTableList(selectStatement);
        for (Iterator<String> iter = tableList.iterator(); iter.hasNext();) {
            System.out.println("===> table name : " + iter.next());
        }
    }

}

package com.yicj.study.spel;

import org.junit.jupiter.api.Test;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * <p>
 * MethodInvokeExpression
 * </p>
 *
 * @author yicj
 * @since 2024年08月03日 21:18
 */
public class MethodInvokeExpressionTest {
    @Test
    void hello(){
        ExpressionParser parser = new SpelExpressionParser();
        String subString = parser.parseExpression("'Hello,SpEL'.substring(0,5)").getValue(String.class);
        System.out.println(subString);
    }

}

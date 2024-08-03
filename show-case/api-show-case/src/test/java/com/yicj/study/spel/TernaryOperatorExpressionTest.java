package com.yicj.study.spel;

import com.yicj.study.model.Inventor;
import org.junit.jupiter.api.Test;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * <p>
 * TernaryOperatorExpression
 * </p>
 *
 * @author yicj
 * @since 2024年08月03日 21:32
 */
public class TernaryOperatorExpressionTest {

    @Test
    void hello(){
        // 表达式内部使用三元运算符执行if-then-else条件逻辑。
        ExpressionParser parser = new SpelExpressionParser();
        String falseString = parser.parseExpression(
                "false ? 'trueExp' : 'falseExp'").getValue(String.class);
        System.out.println("false ? 'trueExp' : 'falseExp' : " + falseString);

        // The Elvis operator (精简版 三元表达式)
        String name = parser.parseExpression("name?:'Unknown'").getValue(new Inventor(), String.class);
        System.out.println(name);  // 'Unknown'

    }
}

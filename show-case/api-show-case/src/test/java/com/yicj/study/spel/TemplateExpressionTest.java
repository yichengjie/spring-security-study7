package com.yicj.study.spel;

import org.junit.jupiter.api.Test;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * <p>
 * TemplateExpression
 * </p>
 *
 * @author yicj
 * @since 2024年08月03日 21:39
 */
public class TemplateExpressionTest {

    @Test
    void hello(){
        // 表达式执行一个模板，并在这个模板中通过 #{} 来占位，示例如下:
        ExpressionParser parser = new SpelExpressionParser();
        String randomPhrase = parser.parseExpression(
                "random number is #{T(java.lang.Math).random()}",
                new TemplateParserContext()).getValue(String.class);
        System.out.println("random number is #{T(java.lang.Math).random()} : " + randomPhrase);
    }

    public static class TemplateParserContext implements ParserContext {

        public String getExpressionPrefix() {
            return "#{";
        }

        public String getExpressionSuffix() {
            return "}";
        }

        public boolean isTemplate() {
            return true;
        }
    }
}

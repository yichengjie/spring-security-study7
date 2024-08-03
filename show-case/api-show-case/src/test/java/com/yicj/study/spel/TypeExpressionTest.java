package com.yicj.study.spel;

import org.junit.jupiter.api.Test;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * <p>
 * TypeExpression
 * </p>
 *
 * @author yicj
 * @since 2024年08月03日 21:19
 */
public class TypeExpressionTest {

    @Test
    void hello(){
        // 通过 T(xxx) 来表示 Class 的实例，静态方法也可以通过这个方式使用，前面我们已经展示过，
        // 即 T(xxx).method(xxx)，另外值得注意的是，在java.lang包下的类可以不指定全限定名，直接指定类名。
        ExpressionParser parser = new SpelExpressionParser();
        Class dateClass = parser.parseExpression("T(java.util.Date)").getValue(Class.class);
        System.out.println(dateClass);

        Class stringClass = parser.parseExpression("T(String)").getValue(Class.class);
        System.out.println(stringClass);

        boolean trueValue = parser.parseExpression(
                        "T(java.math.RoundingMode).CEILING < T(java.math.RoundingMode).FLOOR")
                .getValue(Boolean.class);
        System.out.println(trueValue);
    }
}

package com.yicj.study.spel;

import com.yicj.study.model.Inventor;
import org.junit.jupiter.api.Test;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * <p>
 * ConstructorExpression
 * </p>
 *
 * @author yicj
 * @since 2024年08月03日 21:21
 */
public class ConstructorExpressionTest {

    @Test
    void hello(){
        // 通过 SpEL 来初始化实例，使用时必须要指定全限定名（包括 java.lang 包下的）
        ExpressionParser parser = new SpelExpressionParser();
        Inventor einstein = parser.parseExpression(
                        "new com.yicj.study.model.Inventor()")
                .getValue(Inventor.class);
        System.out.println(einstein);
    }
}

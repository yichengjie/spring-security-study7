package com.yicj.study.spel;

import com.yicj.study.model.Inventor;
import org.junit.jupiter.api.Test;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.SimpleEvaluationContext;

/**
 * <p>
 * SafeNavigationOperators 安全指针表达式
 *
 * </p>
 *
 * @author yicj
 * @since 2024年08月03日 21:33
 */
public class SafeNavigationOperatorsTest {

    @Test
    void hello(){
        // 为避免出现空指针，我们还可以在 SpEL 中使用 ?.来实现
        ExpressionParser parser = new SpelExpressionParser();
        EvaluationContext context = SimpleEvaluationContext.forReadOnlyDataBinding().build();

        Inventor inventor = new Inventor();
        inventor.setName("markus zhang");
        context.setVariable("inventor", inventor);

        String name = parser.parseExpression("#inventor.name?.#inventor.name").getValue(context, String.class);
        System.out.println(name);  // markus zhang

        inventor.setName(null);
        name = parser.parseExpression("#inventor.name?.#inventor.name").getValue(context, String.class);
        System.out.println(name);  // null - does not throw NullPointerException!!!
    }
}

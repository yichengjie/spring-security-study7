package com.yicj.study.spel;

import com.yicj.study.model.builder.InventorBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.SimpleEvaluationContext;

import java.lang.reflect.Method;

/**
 * <p>
 * MethodExpression
 * </p>
 *
 * @author yicj
 * @since 2024年08月03日 21:29
 */
public class MethodExpressionTest {

    @Test
    void hello() throws NoSuchMethodException {
        // 可以向 EvaluationContext中注册函数，并在 SpEL 中使用
        Method method = MethodExpressionTest.class.getDeclaredMethod("display", Object.class);

        EvaluationContext context = SimpleEvaluationContext.forReadWriteDataBinding().build();
        context.setVariable("display", method);
        context.setVariable("inventor", InventorBuilder.builder().build());

        ExpressionParser parser = new SpelExpressionParser();
        parser.parseExpression("#display(#inventor)").getValue(context);
    }

    // must be static method
    public static void display(Object obj) {
        System.out.println(obj);
    }
}

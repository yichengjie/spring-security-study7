package com.yicj.study.spel;

import com.yicj.study.model.Inventor;
import com.yicj.study.model.builder.InventorBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.SimpleEvaluationContext;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * VariableExpression
 * </p>
 *
 * @author yicj
 * @since 2024年08月03日 21:23
 */
public class VariableExpressionTest {

    @Test
    void hello(){
        // 通过使用 #variableName 来获取执行的变量值
        Inventor inventor = InventorBuilder
                .builder()
                .build();
        inventor.getBooleans().add(true);
        inventor.getBooleans().add(false);

        EvaluationContext context = SimpleEvaluationContext.forReadWriteDataBinding().build();
        context.setVariable("name", "Luna");

        ExpressionParser parser = new SpelExpressionParser();
        parser.parseExpression("name = #name").getValue(context, inventor);
        System.out.println(inventor);

        // #this and #root
        // #this 总是指向当前表达式中计算的对象
        // #root 总是指向根对象

        List<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(2);
        integers.add(3);
        integers.add(4);
        integers.add(5);
        integers.add(6);
        context.setVariable("integers", integers);
        String thisExpression = "#integers.?[#this > 3]";
        @SuppressWarnings("unchecked")
        List<Integer> gt3List = parser.parseExpression(thisExpression).getValue(context, List.class);
        gt3List.forEach(integer -> System.out.print(integer + " "));
        System.out.println();

        // #root
        context = new StandardEvaluationContext(inventor);
        Inventor value = parser.parseExpression("#root").getValue(context, Inventor.class);
        System.out.println(value);
    }
}

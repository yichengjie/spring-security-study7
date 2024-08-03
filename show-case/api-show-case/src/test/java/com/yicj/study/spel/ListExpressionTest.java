package com.yicj.study.spel;

import com.yicj.study.model.Inventor;
import com.yicj.study.model.builder.InventorBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * ListExpression
 * </p>
 *
 * @author yicj
 * @since 2024年08月03日 21:10
 */
public class ListExpressionTest {

    @Test
    void hello(){
        Inventor inventor = InventorBuilder.builder()
                .name("Nikola Tesla")
                .booleans(null)
                .build();
        List<Inventor> inventors = new ArrayList<>();
        inventors.add(inventor);
        EvaluationContext context = new StandardEvaluationContext(inventors);

        ExpressionParser parser = new SpelExpressionParser();
        // by [index] get element
        Inventor inventorFromParser = parser.parseExpression("[0]").getValue(context, Inventor.class);
        System.out.println(inventorFromParser);

        // inline list
        // 1. simple type
        @SuppressWarnings("unchecked")
        List<Integer> integers = parser.parseExpression("{1,2,3,4,5}").getValue(context, List.class);
        System.out.println(integers);
        // 2. complex type
        @SuppressWarnings("unchecked")
        List<Inventor> inventorList = (List<Inventor>) parser.parseExpression("{T(com.yicj.study.model.builder.InventorBuilder).builder().build()}").getValue(context);
        System.out.println(inventorList);
    }
}

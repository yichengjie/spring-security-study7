package com.yicj.study.spel;

import com.yicj.study.model.Inventor;
import com.yicj.study.model.builder.InventorBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * PropertiesExpression
 * </p>
 *
 * @author yicj
 * @since 2024年08月03日 20:43
 */
public class PropertiesExpressionTest {

    @Test
    void hello(){
        Inventor inventor = InventorBuilder.builder()
                .name("Nikola Tesla")
                .booleans(Arrays.asList(true, false))
                .build();
        EvaluationContext context = new StandardEvaluationContext(inventor);

        ExpressionParser parser = new SpelExpressionParser();
        String name = parser.parseExpression("name").getValue(context, String.class);
        System.out.println(name);

        // nested property
        int length = parser.parseExpression("name.length").getValue(context, int.class);
        System.out.println(length);
    }

    @Test
    void hello2(){
        Inventor inventor = InventorBuilder.builder()
                .name("markus")
                .booleans(Arrays.asList(true, false))
                .build();
        Map<String, Inventor> map = new HashMap<>();
        map.put("markus", inventor);
        EvaluationContext context = new StandardEvaluationContext(map);

        ExpressionParser parser = new SpelExpressionParser();
        // by ['key'] get element
        //Inventor inventorFromParser = parser.parseExpression("['markus']").getValue(context, Inventor.class);
        Inventor inventorFromParser = parser.parseExpression("['markus']").getValue(context, Inventor.class);
        System.out.println(inventorFromParser);
    }
}

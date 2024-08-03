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
 * MapExpression
 * </p>
 *
 * @author yicj
 * @since 2024年08月03日 21:15
 */
public class MapExpressionTest {

    @Test
    void hello(){
        Inventor inventor = InventorBuilder.builder()
                .name("markus")
                .booleans(Arrays.asList(true, false))
                .build();
        Map<String, Inventor> map = new HashMap<>();
        map.put("markus", inventor);
        EvaluationContext context = new StandardEvaluationContext(map);

        ExpressionParser parser = new SpelExpressionParser();
        // by ['key'] get element
        Inventor inventorFromParser = parser.parseExpression("['markus']").getValue(context, Inventor.class);
        System.out.println(inventorFromParser);

        // nested property access
        String name = parser.parseExpression("['markus'].name").getValue(context, String.class);
        System.out.println(name);

        // inline map
        @SuppressWarnings("unchecked")
        Map<String, Inventor> inventorMap = parser
                .parseExpression("{'markus':T(com.yicj.study.model.builder.InventorBuilder).builder().build()}")
                .getValue(context, Map.class);
        System.out.println(inventorMap);
    }
}

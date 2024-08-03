package com.yicj.study.spel;

import com.yicj.study.model.Inventor;
import com.yicj.study.model.builder.InventorBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * ArraysExpression
 * </p>
 *
 * @author yicj
 * @since 2024年08月03日 20:52
 */
@Slf4j
public class ArraysExpressionTest {

    @Test
    void test1(){
        log.info("------------------ test1 ------------------");
        List<Boolean> list = Arrays.asList(true, false);
        // list.add(true) ; // UnsupportedOperationException
        list.add(true) ;
        System.out.println(list);
    }

    @Test
    void hello(){
        List<Boolean> booleans = new ArrayList<>();
        booleans.add(true);
        booleans.add(false);
        //
        ExpressionParser parser = new SpelExpressionParser();
        // array
        Inventor[] inventors = {
            InventorBuilder.builder()
                .name("Nikola Tesla")
                .booleans(booleans)
                .build()
        };
        EvaluationContext context = new StandardEvaluationContext(inventors);

        // index 表达
        Inventor inventor = parser.parseExpression("[0]").getValue(context, Inventor.class);
        System.out.println(inventor);

        // index + nested property
        String name = parser.parseExpression("[0].name").getValue(context, String.class);
        System.out.println(name);

        // index + nested property index
        inventor.getBooleans().add(true);
        boolean bool = parser.parseExpression("[0].booleans[0]").getValue(context, boolean.class);
        System.out.println(bool);

        // array construction
        int[] numbers = parser.parseExpression("new int[]{1,2,3}").getValue(context, int[].class);
        for (int number : numbers) {
            System.out.print(number + " ");
        }
        System.out.println("\n---------------------------------------");

        // complex type arrays constructor
        Inventor[] complexTypeArrays = parser
                .parseExpression("new com.yicj.study.model.Inventor[1]")
                .getValue(context, Inventor[].class);
        complexTypeArrays[0] = InventorBuilder
                .builder()
                .name("Nikola Tesla")
                .booleans(Arrays.asList(true, false))
                .build()
        ;
        Arrays.stream(complexTypeArrays).forEach(System.out::println);
    }
}

package com.yicj.study.spel;

import org.junit.jupiter.api.Test;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * CollectionSelectionExpression
 * </p>
 *
 * @author yicj
 * @since 2024年08月03日 21:35
 */
public class CollectionSelectionExpressionTest {

    @Test
    void hello(){
        //Selection是一种强大的表达语言功能，它允许您通过从其条目中进行选择，将源集合转换为另一个集合。
        // 选择使用的语法是.?[selectionExpression]。它过滤集合并返回一个包含原始元素子集的新集合
        EvaluationContext context = new StandardEvaluationContext();
        List<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(2);
        integers.add(3);
        integers.add(4);
        integers.add(5);
        integers.add(6);
        context.setVariable("integers", integers);

        ExpressionParser parser = new SpelExpressionParser();
        @SuppressWarnings("unchecked")
        List<Integer> list = (List<Integer>) parser.parseExpression(
                "#integers.?[#this == 3 || #this == 4]").getValue(context);
        list.forEach(ele -> {
            System.out.print(ele + " ");
        });
    }
}

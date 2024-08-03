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
 * CollectionProjectionExpression
 * </p>
 *
 * @author yicj
 * @since 2024年08月03日 21:37
 */
public class CollectionProjectionExpressionTest {

    @Test
    void hello(){
        //集合子集 意为 获取目标集合中某一字段集合，示例如下:
        EvaluationContext context = new StandardEvaluationContext();
        List<Inventor> inventors = new ArrayList<>();
        inventors.add(InventorBuilder.builder().build());
        inventors.add(InventorBuilder.builder().name("Luna").build());
        context.setVariable("inventors", inventors);

        ExpressionParser parser = new SpelExpressionParser();
        @SuppressWarnings("unchecked")
        List<String> list = (List<String>) parser.parseExpression(
                "#inventors.![name]").getValue(context);
        list.forEach(ele -> {
            System.out.println(ele + " ");
        });
    }
}

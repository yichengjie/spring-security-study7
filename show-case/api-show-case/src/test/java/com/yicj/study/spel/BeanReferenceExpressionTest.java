package com.yicj.study.spel;

import com.yicj.study.model.Inventor;
import org.junit.jupiter.api.Test;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * <p>
 * BeanReferenceExpression
 * </p>
 *
 * @author yicj
 * @since 2024年08月03日 21:31
 */
public class BeanReferenceExpressionTest {

    @Test
    void hello(){
        // 通过 @xxx 来获取 Spring 容器中的 Bean 实例
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:/META-INF/expression-in-bean-definitions.xml");

        BeanFactoryResolver beanFactoryResolver = new BeanFactoryResolver(context.getBeanFactory());
        StandardEvaluationContext evaluationContext = new StandardEvaluationContext();
        evaluationContext.setBeanResolver(beanFactoryResolver);

        ExpressionParser parser = new SpelExpressionParser();
        String expression = "@inventor";
        Inventor inventor = parser.parseExpression(expression).getValue(evaluationContext, Inventor.class);
        System.out.println(inventor);
    }
}

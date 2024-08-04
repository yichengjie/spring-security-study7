package com.yicj.study.bodyadvice.anno;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Indexed;
import java.lang.annotation.*;

/**
 * <p>
 * EraseAnno
 * </p>
 *
 * @author yicj
 * @since 2024年07月25日 15:43
 */
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ErasePasswordAnno {
    @AliasFor(attribute = "expression")
    String value() default "" ;

    @AliasFor(attribute = "value")
    String expression() default "" ;
}

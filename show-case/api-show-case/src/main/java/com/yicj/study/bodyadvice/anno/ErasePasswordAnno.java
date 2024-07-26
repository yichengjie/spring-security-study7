package com.yicj.study.bodyadvice.anno;

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
@Indexed
public @interface ErasePasswordAnno {

    String value() default "" ;

}

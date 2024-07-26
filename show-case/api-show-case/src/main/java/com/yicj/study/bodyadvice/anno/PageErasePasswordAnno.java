package com.yicj.study.bodyadvice.anno;

import org.springframework.core.annotation.AliasFor;
import java.lang.annotation.*;

/**
 * <p>
 * PageErasePasswordAnno
 * </p>
 *
 * @author yicj
 * @since 2024年07月26日 10:58
 */
@Target({ElementType.METHOD, })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ErasePasswordAnno
public @interface PageErasePasswordAnno {

    @AliasFor(annotation = ErasePasswordAnno.class)
    String value() default "pageValueExtractor" ;
}

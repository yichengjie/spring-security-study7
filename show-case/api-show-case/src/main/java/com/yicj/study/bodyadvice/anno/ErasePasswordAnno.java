package com.yicj.study.bodyadvice.anno;

import com.yicj.study.bodyadvice.func.ValueFunction;
import org.springframework.stereotype.Indexed;
import java.lang.annotation.*;
import java.util.function.Function;

/**
 * <p>
 * EraseAnno
 * </p>
 *
 * @author yicj
 * @since 2024年07月25日 15:43
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface ErasePasswordAnno {

}

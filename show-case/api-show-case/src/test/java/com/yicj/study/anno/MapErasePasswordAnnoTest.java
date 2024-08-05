package com.yicj.study.anno;

import com.yicj.study.bodyadvice.anno.ErasePasswordAnno;
import com.yicj.study.bodyadvice.anno.MapErasePasswordAnno;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;

/**
 * <p>
 * MapErasePasswordAnnoTest
 * </p>
 *
 * @author yicj
 * @since 2024年08月04日 21:11
 */
@Slf4j
public class MapErasePasswordAnnoTest {

    @Test
    void simple() throws NoSuchMethodException {
        Method method = MapErasePasswordAnnoTest.class.getMethod("hello");
        ErasePasswordAnno annotation = AnnotationUtils.getAnnotation(method, ErasePasswordAnno.class);
        Assertions.assertNotNull(annotation);
        log.info("annotation value : {}", annotation.value());
        log.info("annotation expression : {}", annotation.value());
    }

    @Test
    void complex() throws NoSuchMethodException {
        Method method = MapErasePasswordAnnoTest.class.getMethod("hello2");
        ErasePasswordAnno annotation = AnnotatedElementUtils.findMergedAnnotation(method, ErasePasswordAnno.class);
        Assertions.assertNotNull(annotation);
        log.info("annotation value : {}", annotation.value());
        log.info("annotation expression : {}", annotation.value());
    }

    @ErasePasswordAnno("username")
    public void hello(){

    }

    @MapErasePasswordAnno
    //@ErasePasswordAnno("username")
    public void hello2(){

    }
}

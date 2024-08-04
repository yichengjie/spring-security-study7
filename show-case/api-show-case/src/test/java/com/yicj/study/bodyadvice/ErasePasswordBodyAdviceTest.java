package com.yicj.study.bodyadvice;


import com.yicj.study.bodyadvice.anno.ErasePasswordAnno;
import com.yicj.study.controller.HelloController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.Method;
import java.util.Optional;

/**
 * <p>
 * ErasePasswordBodyAdviceTest
 * </p>
 * @author yicj
 * @since 2024年07月28日 15:34
 */
@Slf4j
public class ErasePasswordBodyAdviceTest {

    @Test
    void findById() throws NoSuchMethodException {
        // ResultEntity<BasicUser> findById(@PathVariable String id){
        Method method = HelloController.class.getMethod("findById", String.class);
        String value = getExtractorName(method);
        log.info("value : {}", value);
    }

    @Test
    void listForPage() throws NoSuchMethodException {
        // ResultEntity listForPage()
        Method method = HelloController.class.getMethod("findPage");
        String value = getExtractorName(method);
        log.info("value : {}", value);
    }

    @Test
    void listForPageMono() throws NoSuchMethodException {
        // ResultEntity listForPage()
        Method method = HelloController.class.getMethod("findPage");
        Mono<String> mono = getExtractorNameMono(method);
        mono.subscribe(value -> log.info("value : {}", value));
    }

    @Test
    void concatMap(){
        Flux.range(1, 10)
            .concatMap(item -> Mono.just(item + " world"))
            .subscribe(System.out::println);
    }

    private Mono<String> getExtractorNameMono(Method method){
        return monoForErasePasswordAnno(method)
                .switchIfEmpty(monoForMapErasePasswordAnno(method))
                ;
    }

    private Mono<String> monoForErasePasswordAnno(Method method){
        return Mono.just(method)
                .filter(item -> item.isAnnotationPresent(ErasePasswordAnno.class))
                .map(item -> item.getAnnotation(ErasePasswordAnno.class))
                .map(ErasePasswordAnno::value)
                .filter(StringUtils::isNotBlank);
    }

    private Mono<String> monoForMapErasePasswordAnno(Method method){
        return  Mono.just(method).filter(item -> item.isAnnotationPresent(ErasePasswordAnno.class))
                .map(item -> item.getAnnotation(ErasePasswordAnno.class))
                .map(ErasePasswordAnno::value)
                .filter(StringUtils::isNotBlank) ;
    }


    private String getExtractorName(Method method){
        return  Optional.ofNullable(method.getAnnotation(ErasePasswordAnno.class))
                .map(ErasePasswordAnno::value)
                .filter(StringUtils::isNotBlank)
                .map(String::trim)
                .orElseGet(() -> {
                    ErasePasswordAnno pageErasePasswordAnno = method.getAnnotation(ErasePasswordAnno.class);
                    return pageErasePasswordAnno != null ? pageErasePasswordAnno.value() : null;
                });
    }
}

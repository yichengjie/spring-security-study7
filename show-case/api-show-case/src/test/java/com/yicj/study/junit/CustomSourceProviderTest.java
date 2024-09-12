package com.yicj.study.junit;

import lombok.extern.slf4j.Slf4j;
import org.apiguardian.api.API;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import java.lang.annotation.*;
import java.util.stream.Stream;
import static org.apiguardian.api.API.Status.STABLE;

/**
 * @author yicj
 * @since 2024/9/12 21:40
 */
@Slf4j
class CustomSourceProviderTest {

    @ParameterizedTest
    @MySource({"hello", "world"})
    void hello(String name){
        log.info("custom arg provider ==> name: {}", name) ;
    }


    @Target({ ElementType.ANNOTATION_TYPE, ElementType.METHOD })
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @API(status = STABLE, since = "5.7")
    @ArgumentsSource(MySourceProvider.class)
    @interface MySource {

        String [] value() ;
    }

    static class MySourceProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
            String [] values = context.getTestMethod()
                    .map(method -> method.getAnnotation(MySource.class))
                    .map(MySource::value).orElse(null);
            if(values == null){
                return Stream.empty() ;
            }
            return Stream.of(values)
                    .map(Arguments::of) ;
        }
    }
}

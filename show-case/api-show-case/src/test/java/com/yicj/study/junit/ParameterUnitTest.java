package com.yicj.study.junit;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import java.util.stream.Stream;

/**
 * @author yicj
 * @since 2024/9/12 21:41
 */
@Slf4j
class ParameterUnitTest {

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    void valueSourceInt(int number) {
        log.info("number : {}", number);
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "b", "c"})
    void valueSourceString(String title) {
        log.info("value source string ==>  title : {}", title);
    }

    @ParameterizedTest
    @CsvSource({"1, One", "2, Two", "3, Three"})
    void csvSource(long id, String name) {
        log.info("csv source ==> id : {}, name : {}", id, name);
    }

    @ParameterizedTest(name = "{index} => id:{0}, name:{1}")
    @ArgumentsSource(MyArgumentsProvider.class)
    void argumentsSource(String name, Integer number){

        log.info("arguments source ==> name : {}, number : {}", name, number);
    }

    @ParameterizedTest
    @MethodSource("methodSourceParams")
    void methodSource(String name, Integer number){
        log.info("method source ==> name : {}, number : {}", name, number);
    }

    @ParameterizedTest
    @EnumSource(value = MyEnum.class, names = {"ONE", "TWO"})
    void enumSource(MyEnum myEnum){
        log.info("enum source ==> my enum : {}", myEnum);
    }

    static class MyArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
            return Stream.of(
                Arguments.of("John", 1),
                Arguments.of("Jane", 2),
                Arguments.of("Doe", 3)
            );
        }
    }

    public static Stream<Arguments> methodSourceParams() {
        return Stream.of(
                Arguments.of("a", 1),
                Arguments.of("b", 2),
                Arguments.of("c", 3)
        );
    }

    enum MyEnum{
        ONE, TWO, THREE
    }

}

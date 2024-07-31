package com.yicj.study.reactor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import java.util.stream.Stream;
import static java.util.stream.Collectors.joining;

@Slf4j
public class StreamTest {

    @Test
    void generate(){
        Stream<String> stream10 = Stream.generate(() -> "A").limit(3);
        log.info("stream10: {}" , stream10.collect(joining()));
    }

}

package org.springframework.cloud.gateway.filter.headers;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author yicj
 * @since 2024/9/17 9:44
 */
@Slf4j
public class ForwardedHeadersFilterTest {

    @Test
    void parse(){
        ForwardedHeadersFilter.Forwarded result = ForwardedHeadersFilter.parse("for=a, for=b, for=c");
        log.info("result : {}", result.getValues());
    }
}

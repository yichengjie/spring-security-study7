package com.yicj.message.component;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import java.util.Collections;

/**
 * @author yicj
 * @since 2024/9/15 21:07
 */
@Slf4j
class MyPollableChannelTest {

    @Test
    void hello(){
        MyPollableChannel channel = new MyPollableChannel() ;
        channel.send(MessageBuilder.withPayload("custom payload1")
            .setHeader("k1","v1").build()
        );
        channel.send(MessageBuilder.withPayload("custom payload2")
            .setHeader("k2","v2").build()
        );
        channel.send(MessageBuilder.createMessage("custom payload3",
            new MessageHeaders(Collections.singletonMap("ignore", true)))
        ) ;
        log.info("channel receive message : {}", channel.receive());
        log.info("channel receive message : {}", channel.receive());
        log.info("channel receive message : {}", channel.receive());
        log.info("channel receive message : {}", channel.receive());
    }
}

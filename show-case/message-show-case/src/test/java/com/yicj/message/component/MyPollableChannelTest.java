package com.yicj.message.component;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.PollableChannel;
import org.springframework.messaging.support.MessageBuilder;
import java.util.Collections;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

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

    @Slf4j
    static class MyPollableChannel implements PollableChannel {

        private final BlockingQueue<Message<?>> queue = new ArrayBlockingQueue<>(1000) ;

        @Override
        public Message<?> receive() {
            return queue.poll();
        }

        @Override
        public Message<?> receive(long timeout) {
            try {
                return queue.poll(timeout, TimeUnit.MICROSECONDS) ;
            }catch (InterruptedException e){
                log.error("receive message error : ", e);
                Thread.currentThread().interrupt();
            }
            return null;
        }

        @Override
        public boolean send(@NotNull Message<?> message, long timeout) {
            return queue.add(message);
        }
    }
}

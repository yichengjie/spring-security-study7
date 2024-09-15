package com.yicj.message.component;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;

import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yicj
 * @since 2024/9/15 20:01
 */
@Slf4j
class MySubscribableChannelTest {

    @Test
    void hello(){
        AtomicInteger successCount = new AtomicInteger() ;
        AtomicInteger failCount = new AtomicInteger() ;
        MySubscribableChannel channel = new MySubscribableChannel() ;
        channel.addInterceptor(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                String ignoreKey = "ignore" ;
                if (message.getHeaders().containsKey(ignoreKey)
                        && Boolean.TRUE.equals(message.getHeaders().get(ignoreKey, Boolean.class))){
                    log.info("ignore message : {}", message);
                    return null ;
                }
                return message;
            }

            @Override
            public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
                //log.info("postSend message : {}", message);
            }

            @Override
            public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
                //log.info("afterSendCompletion message : {}", message);
                if (sent){
                    successCount.incrementAndGet() ;
                }else {
                    failCount.incrementAndGet() ;
                }
            }
        });

        // 发送消息
        channel.send(MessageBuilder.withPayload("custom payload1")
            .setHeader("k1","v1").build()
        );

        channel.subscribe(msg -> log.info("handle1 receive message : {}", msg)) ;
        channel.subscribe(msg -> log.info("handle2 receive message : {}", msg)) ;
        channel.subscribe(msg -> log.info("handle3 receive message : {}", msg)) ;

        // 发送消息
        channel.send(MessageBuilder.withPayload("custom payload2")
            .setHeader("k2","v2").build()
        );
        channel.send(MessageBuilder.createMessage("custom payload3",
            new MessageHeaders(Collections.singletonMap("ignore", true)))
        );
        log.info("successCount : {}, failCount : {}", successCount.get(), failCount.get());
    }
}

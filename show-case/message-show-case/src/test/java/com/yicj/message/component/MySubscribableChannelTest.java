package com.yicj.message.component;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.messaging.*;
import org.springframework.messaging.support.AbstractSubscribableChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
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

    @Slf4j
    static class MySubscribableChannel extends AbstractSubscribableChannel {

        private final Random random = new Random() ;

        @Override
        protected boolean sendInternal(@NotNull Message<?> message, long timeout) {
            if (CollectionUtils.isEmpty(getSubscribers())){
                return false ;
            }
            Iterator<MessageHandler> iterator = getSubscribers().iterator();
            // 记录当前处理的下标
            int index = 0 ,targetIndex = random.nextInt(getSubscribers().size());
            // 随机选择一个下标
            while (iterator.hasNext()){
                MessageHandler messageHandler = iterator.next();
                log.info("subscriber count : {}, current index : {}, target index : {}", getSubscribers().size(), index, targetIndex);
                // 如果当前下标等于随机下标，则处理消息
                if (index == targetIndex){
                    messageHandler.handleMessage(message);
                    return true ;
                }
                index ++ ;
            }
            return false;
        }
    }
}

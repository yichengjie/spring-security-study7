package com.yicj.message.component;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.messaging.Message;
import org.springframework.messaging.PollableChannel;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author yicj
 * @since 2024/9/15 21:04
 */
@Slf4j
public class MyPollableChannel implements PollableChannel {

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

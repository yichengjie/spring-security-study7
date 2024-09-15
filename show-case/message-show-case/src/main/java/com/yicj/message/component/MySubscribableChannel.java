package com.yicj.message.component;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.support.AbstractSubscribableChannel;
import org.springframework.util.CollectionUtils;

import java.util.Iterator;
import java.util.Random;

/**
 * @author yicj
 * @since 2024/9/15 19:52
 */
@Slf4j
public class MySubscribableChannel extends AbstractSubscribableChannel {

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

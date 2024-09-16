package com.yicj.message.component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

/**
 * @author yicj
 * @since 2024/9/16 12:15
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class KafkaChannel {

    private final StreamBridge streamBridge ;

    @Bean
    public Consumer<Message<String>> inputChannel(){
        return message -> {
            log.info("Received message body : {}", message.getPayload()); ;
            log.info("Received message header : {}", message.getHeaders()); ;
        } ;
    }

}

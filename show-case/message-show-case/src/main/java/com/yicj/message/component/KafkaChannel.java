package com.yicj.message.component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import java.util.List;
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
    public Consumer<Message<List<String>>> inputChannel(){
        return message -> {
            List<String> payload = message.getPayload();
            // message size
            log.info("Received message size : {}", payload.size());
            payload.forEach(item -> {
                log.info("Received message : {}", item);
            });
            log.info("Received message header : {}", message.getHeaders());
            MessageHeaders headers = message.getHeaders();
            Acknowledgment acknowledgment = headers.get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);
            if (acknowledgment != null) {
                acknowledgment.acknowledge();
            }
        } ;
    }

}

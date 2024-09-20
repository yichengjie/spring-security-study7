package com.yicj.study.kafka.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @author yicj
 * @since 2024/9/20 22:12
 */
@Component
@RequiredArgsConstructor
public class HelloProducer {

    private final KafkaTemplate<Object, Object> kafkaTemplate ;

    @Value("${app.kafka.topic:hello-topic}")
    private String topicName ;

    public void send(String content){
        send(null, content);
    }

    public void send(String key, String content){
        kafkaTemplate.send(topicName, key, content) ;
    }
}

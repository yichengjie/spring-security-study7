package com.yicj.study.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

/**
 * @author yicj
 * @since 2024/9/20 22:06
 */
@Slf4j
@Component
public class HelloConsumer {
    @KafkaListener(
        id = "helloMessageConsumer",
        groupId = "${app.kafka.groupId:hello-group}",
        containerFactory = "kafkaListenerContainerFactory",
        topics = "${app.kafka.topic:hello-topic}"
    )
    public void listener(ConsumerRecord<String, Object> record, Acknowledgment ack) {
        String content = (String) record.value();
        try {
            log.info("kafka message : {}", content) ;
            // todo 业务方法处理
        } catch (Exception e) {
            log.error("消息异常信息: ", e);
        } finally {
            ack.acknowledge();
        }
    }
}

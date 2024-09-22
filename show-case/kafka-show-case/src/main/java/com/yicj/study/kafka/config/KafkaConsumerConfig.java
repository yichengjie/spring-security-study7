package com.yicj.study.kafka.config;

import lombok.Data;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yicj
 * @since 2024/9/20 18:13
 */
@Configuration
@EnableConfigurationProperties(KafkaConsumerConfig.ConsumerProperties.class)
public class KafkaConsumerConfig {

    @Bean("kafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, String> containerFactory(ConsumerProperties properties){
        Map<String, Object> factoryBeanProp = this.assembleConsumerProperties(properties);
        // consumer factory
        var consumerFactory = new DefaultKafkaConsumerFactory<>(factoryBeanProp);
        // container factory
        var containerFactory = new ConcurrentKafkaListenerContainerFactory<String, String>();
        containerFactory.setConsumerFactory(consumerFactory);
        containerFactory.getContainerProperties().setPollTimeout(properties.getPollTimeout());
        if (!Boolean.TRUE.equals(properties.getAutoCommit())) {
            containerFactory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        }
        return containerFactory ;
    }


    private Map<String, Object> assembleConsumerProperties(ConsumerProperties consumer){
        Map<String, Object> props = new HashMap<>();
        String bootstrapServers = consumer.getBootstrapServers() ;
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, consumer.getGroupId());
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, consumer.getAutoCommit());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, consumer.getAutoOffsetReset());
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, consumer.getAutoCommitInterval());
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, consumer.getSessionTimeout());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, consumer.getKeyDeserializer());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, consumer.getValueDeserializer());
        props.put("listener.ack-mode", "MANUAL_IMMEDIATE");
        if (consumer.getMaxPollRecords() != null && consumer.getMaxPollRecords() > 0){
            props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, consumer.getMaxPollRecords()) ;
        }
        // security config
        //props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, consumer.getSecurityProtocol());
        //props.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, "${user.home}/client_truststore.jks");
        //props.put("ssl.truststore.password", consumer.getSslTruststorePassword());
        //props.put("sasl.mechanism", "SCRAM-SHA-512");
        //props.put("sasl.jaas.config",
        //        "org.apache.kafka.common.security.scram.ScramLoginModule required username='"+consumer.getUsername()+"' password='"+consumer.getPassword()+"';");
        //props.put(SslConfigs.SSL_ENDPOINT_IDENTIFICATION_ALGORITHM_CONFIG, "");
        return props ;
    }


    @Data
    @ConfigurationProperties(prefix = "app.kafka.consumer")
    public static class ConsumerProperties{
        private String bootstrapServers = "localhost:9092" ;
        private String groupId = "hello-group";
        private Boolean autoCommit = false;
        private String autoOffsetReset = "latest" ;
        private Integer autoCommitInterval = 1000 ;
        private Integer sessionTimeout = 60000;
        private String keyDeserializer = "org.apache.kafka.common.serialization.StringDeserializer";
        private String valueDeserializer = "org.apache.kafka.common.serialization.StringDeserializer";
        private Integer maxPollRecords = 500;
        private Integer pollTimeout = 1500 ;
        // security config
        private String securityProtocol = "SASL_SSL";
    }
}

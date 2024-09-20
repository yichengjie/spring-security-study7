package com.yicj.study.kafka.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.config.SslConfigs;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yicj
 * @since 2024/9/20 18:14
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(KafkaProducerConfig.ProducerProperties.class)
public class KafkaProducerConfig {

    @Bean("kafkaTemplate")
    public KafkaTemplate<Object, Object> kafkaTemplate(ProducerProperties properties){
        Map<String, Object> factoryBeanProp = this.assembleProducerProperties(properties);
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(factoryBeanProp));
    }

    private Map<String, Object> assembleProducerProperties(ProducerProperties properties){
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
        props.put(ProducerConfig.RETRIES_CONFIG, properties.getRetries());
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, properties.getBatchSize());
        props.put(ProducerConfig.LINGER_MS_CONFIG, properties.getLinger());
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, properties.getBufferMemory());
        props.put(ProducerConfig.ACKS_CONFIG, properties.getAcks());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, properties.getKeySerializer());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, properties.getValueSerializer());
        props.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, 105610900) ;
        // security config
        //props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, properties.getSecurityProtocol());
        //props.put(SaslConfigs.SASL_MECHANISM, "SCRAM-SHA-512"); //SCRAM-SHA-512 //SCRAM-SHA-512
        //props.put(SaslConfigs.SASL_JAAS_CONFIG,
        //        "org.apache.kafka.common.security.scram.ScramLoginModule required username='"+properties.getUsername()+"' password='"+properties.getPassword()+"';");
        //props.put(SslConfigs.SSL_ENDPOINT_IDENTIFICATION_ALGORITHM_CONFIG, "");
        //props.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, properties.getSslTruststorePassword());
        //props.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, "${user.home}/client_truststore.jks");
        return props ;
    }

    @Data
    @ConfigurationProperties(prefix = "app.kafka.producer")
    public static class ProducerProperties{
        private String bootstrapServers = "localhost:9092" ;
        private Integer retries = 0 ;
        private Integer batchSize = 16384;
        private Integer linger =1 ;
        private Integer bufferMemory = 33554432;
        private String acks = "1";
        private String keySerializer = "org.apache.kafka.common.serialization.StringSerializer";
        private String valueSerializer = "org.apache.kafka.common.serialization.StringSerializer";
        // security config
        private String securityProtocol = "SASL_SSL" ;
    }
}

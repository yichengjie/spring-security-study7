1. 添加依赖
    ```xml
    <dependency>
        <groupId>org.springframework.kafka</groupId>
        <artifactId>spring-kafka</artifactId>
    </dependency>
    ```
2. 配置KafkaTemplate
    ```java
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
        }
    }
    ```
3. 配置KafkaListenerContainerFactory
    ```java
    @Configuration
    @EnableConfigurationProperties(KafkaConsumerConfig.ConsumerProperties.class)
    public class KafkaConsumerConfig {
    
        @Bean("kafkaListenerContainerFactory")
        public ConcurrentKafkaListenerContainerFactory<String, String> containerFactory(ConsumerProperties properties){
            Map<String, Object> factoryBeanProp = this.assembleConsumerProperties(properties);
            var consumerFactory = new DefaultKafkaConsumerFactory<>(factoryBeanProp);
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
        }
    }
    ```
4. application.yml添加配置
    ```yaml
    app:
      kafka:
        consumer:
          group-id: inputChannel
        topic: kafka-test
    ```
5. 消费者编写
    ```java
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
    ```
6. 生产者编写
    ```java
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
    ```
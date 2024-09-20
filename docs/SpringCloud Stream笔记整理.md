1. 引入依赖
    ```xml
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-stream-binder-kafka</artifactId>
    </dependency>
    ```
2. 消费者
    ```java
    @Configuration
    public class KafkaChannel {
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
    ```
3. 生产者
    ```java
    @RestController
    @RequestMapping("/hello")
    @RequiredArgsConstructor
    public class HelloController {
        private final StreamBridge streamBridge ;
   
        @GetMapping("/send2")
        public Boolean sendMessage2(@RequestParam("msg") String msg) throws JsonProcessingException {
            UserInfoVO userInfo = UserInfoVO.builder()
                    .username("张三")
                    .address("北京")
                    .phone("13888888888")
                    .build() ;
            return streamBridge.send("outputChannel-out-0", userInfo) ;
        }
    }
    ```
4. application.yml中配置kafka
    ```yaml
    spring:
      cloud:
        function:
          definition: inputChannel;outputChannel
        stream:
          default-binder: kafka-cn
          binders:
            kafka-cn:
              type: kafka
              environment:
                spring:
                  cloud:
                    stream:
                      kafka:
                        binder:
                          brokers: localhost:9092
          bindings:
            inputChannel-in-0:
              binder: kafka-cn
              destination: kafka-test
              group: inputChannel
              contentType: text/plain
              consumer:
                batch-mode: true
            outputChannel-out-0:
              binder: kafka-cn
              destination: kafka-test
              contentType: application/json
              producer:
                use-native-encoding: true
          kafka:
            binder:
              configuration:
                key.deserializer: org.apache.kafka.common.serialization.StringDeserializer
                value.deserializer: org.apache.kafka.common.serialization.StringDeserializer
                max.poll.records: 500
                max.poll.interval.ms: 600000
                fetch.max.bytes: 52428800
            bindings:
              inputChannel-in-0:
                consumer:
                  ack-mode: manual_immediate
              outputChannel-out-0:
                producer:
                  configuration:
                    key.serializer: org.apache.kafka.common.serialization.StringSerializer
                    value.serializer: org.springframework.kafka.support.serializer.JsonSerializer    
    ```
5. SSL 配置 (可选)
    ```yaml
    spring:
      cloud:
        stream:
          kafka:
            binder:
              configuration:
                security.protocol: "SASL_SSL"
                sasl.mechanism: "SCRAM-SHA-512"
                ssl.truststore.location: "${user.home}/client_truststore.jks"
                ssl.truststore.password: "hello123"
                ssl.endpoint.identification.algorithm: ""
    ```
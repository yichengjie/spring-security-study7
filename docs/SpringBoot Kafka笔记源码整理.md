### @EnableKafka 注解
1. KafkaBootstrapConfiguration 注入Bean 
    ```text
    KafkaListenerAnnotationBeanPostProcessor
    KafkaListenerEndpointRegistry
    ```
   
### 扫描 KafkaListener注解的Bean
1. KafkaListenerAnnotationBeanPostProcessor#postProcessAfterInitialization(bean, beanName)
2. 获取标注@KafkaListener的所有方法
3. KafkaListenerAnnotationBeanPostProcessor#processKafkaListener(listener, method, bean, beanName)
4. KafkaListenerAnnotationBeanPostProcessor#processListener(endpoint, kafkaListener, bean, beanName, topics, tps)
5. KafkaListenerEndpointRegistrar#registerEndpoint(endpoint, listenerContainerFactory)

### 实例化 KafkaMessageListenerContainer
1. KafkaListenerAnnotationBeanPostProcessor#afterSingletonsInstantiated
2. KafkaListenerEndpointRegistrar#afterPropertiesSet() => registerAllEndpoints()
3. KafkaListenerEndpointRegistry#registerListenerContainer(endpoint,containerFactory, false)
4. KafkaListenerEndpointRegistry#createListenerContainer(endpoint,containerFactory)
5. MessageListenerContainer listenerContainer = factory.createListenerContainer(endpoint);
6. new ConcurrentMessageListenerContainer<>(getConsumerFactory(), properties)

### 接受消息阶段(RecordMessagingMessageListenerAdapter)
1. ConcurrentMessageListenerContainer#doStart() 实例化 KafkaMessageListenerContainer并启动
2. KafkaMessageListenerContainer#doStart()
3. new ListenerConsumer(listener, listenerType, observationRegistry)#run() => pollAndInvoke()
4. ListenerConsumer#invokeIfHaveRecords => invokeListener(records) => invokeRecordListener(records)
5. ListenerConsumer#doInvokeWithRecords(records) => doInvokeRecordListener(records, iterator)
6. ListenerConsumer#invokeOnMessage(records) => doInvokeOnMessage(records)
7. RecordMessagingMessageListenerAdapter#onMessage(record, acknowledgment, consumer)
8. RecordMessagingMessageListenerAdapter#invokeHandler(record, acknowledgment, message, consumer)
9. MessagingMessageListenerAdapter#invokeHandler(record, acknowledgment, message, consumer)
10. HandlerAdapter#invoke(message, data, ack, consumer)
11. InvocableHandlerMethod#invoke(message, providedArgs) => doInvoke(args)
12. KafkaListener标注的业务method中
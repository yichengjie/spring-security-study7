### StreamBridge 发送消息

1. StreamBridge#send

2. SimpleFunctionRegistry#convertOutputIfNecessary

2. SmartCompositeMessageConverter#toMessage

   ```
   0 = {KafkaNullConverter@12757} 
   1 = {CompositeMessageConverterFactory$2@12758} 
   2 = {CompositeMessageConverterFactory$3@12759} 
   3 = {ObjectStringMessageConverter@12760} 
   4 = {JsonMessageConverter@12761} 
   5 = {ByteArrayMessageConverter@12762} 
   6 = {StringMessageConverter@12763} 
   7 = {PrimitiveTypesFromStringMessageConverter@12764} 
   ```

4. JsonMapper#toJson(payload) 将payload 转成byte[] 数组

   ```
   ObjectMapper.writeValueAsBytes(value) 将payload转成byte 数组
   ```

3. messageChannel.send(resultMessage) ->AbstractMessageChannel.send(message) -> sendInternal(message)

4. AbstractMessageChannel#doSend -> DirectWithAttributesChannel#doSend

7. UnicastingDispatcher#dispatch =>doDispatch

8. KafkaMessageChannelBinder#handleMessage  => AbstractMessageHandler#handleMessage

9. KafkaProducerMessageHandler#handleRequestMessage => createProducerRecord

10. KafkaTemplate#send(producerRecord)

11. DefaultKafkaProducerFactory$CloseSafeProducer#send 

12. KafkaProducer#send => doSend key 和value 都使用ByteArraySerializer序列化

13. KafkaProducer#doSend中使用valueSerializer对palyload 序列化

    ```
    serializedValue = valueSerializer.serialize(record.topic(), record.headers(), record.value());
    ```

14. 其他

package com.yicj.message.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yicj.message.model.UserInfoVO;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.springframework.cloud.function.context.message.MessageUtils;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;

import java.util.Collections;

/**
 * @author yicj
 * @since 2024/9/20 13:13
 */
public class StringSerializerTest {

    @Test
    void hello(){
        StringSerializer serializer = new StringSerializer() ;
        byte[] retObj = serializer.serialize("topic", "hello") ;
        System.out.println(new String(retObj));
    }

    @Test
    void hello2(){
        UserInfoVO userInfoVO = UserInfoVO.builder()
                .username("张三")
                .address("北京")
                .phone("123456")
                .build() ;
        JsonSerializer<UserInfoVO> serializer = new JsonSerializer<>() ;
        byte[] retObj = serializer.serialize("topic", userInfoVO) ;
        System.out.println(new String(retObj));
    }


    @Test
    void hello3() throws JsonProcessingException {
        UserInfoVO userInfoVO = UserInfoVO.builder()
                .username("张三")
                .address("北京")
                .phone("123456")
                .build() ;
        ObjectMapper mapper = new ObjectMapper() ;
        byte[] payload = mapper.writeValueAsBytes(userInfoVO);
        System.out.println(new String(payload));

        JsonSerializer<byte[]> valueSerializer = new JsonSerializer<>() ;
        Headers headers = null ;
        byte[] serializeValues = valueSerializer.serialize("test", headers, payload);
        System.out.println(new String(serializeValues));
    }


    @Test
    void hello4(){
        //GenericMessage [payload=UserInfoVO(username=张三, address=北京, phone=13888888888), headers={target-protocol=kafka, id=17cdb01e-2b9a-208a-053b-7796efb2faa3, timestamp=1726822258608}]
        UserInfoVO data = UserInfoVO.builder()
                .username("张三")
                .address("北京")
                .phone("123456")
                .build() ;
        Message<?>  messageToSend = new GenericMessage<>(data, Collections.singletonMap(MessageUtils.TARGET_PROTOCOL, "kafka")); ;
        Message<?> resultMessage = (Message<byte[]>) messageToSend;
        System.out.println(resultMessage);
    }
}

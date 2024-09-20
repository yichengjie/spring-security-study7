package com.yicj.message.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yicj.message.model.UserInfoVO;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yicj
 * @since 2024/9/16 12:09
 */
@RestController
@RequestMapping("/hello")
@RequiredArgsConstructor
public class HelloController {

    private final StreamBridge streamBridge ;

    private final ObjectMapper objectMapper = new ObjectMapper() ;

    @GetMapping("/send")
    public Boolean sendMessage(@RequestParam("msg") String msg) throws JsonProcessingException {
        UserInfoVO userInfo = UserInfoVO.builder()
                .username("张三")
                .address("北京")
                .phone("13888888888")
                .build() ;
        String jsonContent = objectMapper.writeValueAsString(userInfo);
        Message<String> message = MessageBuilder.withPayload(jsonContent).build();
        return streamBridge.send("outputChannel-out-0", message) ;
    }


    @GetMapping("/send2")
    public Boolean sendMessage2(@RequestParam("msg") String msg) throws JsonProcessingException {
        UserInfoVO userInfo = UserInfoVO.builder()
                .username("张三")
                .address("北京")
                .phone("13888888888")
                .build() ;
        return streamBridge.send("outputChannel-out-0", userInfo) ;
    }


    @GetMapping("/send3")
    public Boolean sendMessage3(@RequestParam("msg") String msg) throws JsonProcessingException {
        UserInfoVO userInfo = UserInfoVO.builder()
                .username("张三")
                .address("北京")
                .phone("13888888888")
                .build() ;
        Message<UserInfoVO> message = MessageBuilder.withPayload(userInfo)
                .setHeader("partitionKey", userInfo.getUsername())
                .build();
        return streamBridge.send("outputChannel-out-0", message) ;
    }

}

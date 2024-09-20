package com.yicj.message.controller;

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

    @GetMapping("/send")
    public Boolean sendMessage(@RequestParam("msg") String msg){
        //Message<String> message = MessageBuilder.withPayload("kafka测试：" + msg).build();
        UserInfoVO userInfo = UserInfoVO.builder()
                .username("张三")
                .address("北京")
                .phone("13888888888")
                .build() ;
        return streamBridge.send("outputChannel-out-0", userInfo) ;
    }

}

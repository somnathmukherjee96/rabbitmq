package com.som.mq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessagePublisher {

    @Autowired private RabbitTemplate template;
    @PostMapping("/user")
    public String publishMessage(@RequestBody UserRegistrationRequest userRegistrationRequest){
        template.convertAndSend("x.user-registration","user-registration", userRegistrationRequest);
        return "Message Published";
    }
}

package com.som.mq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {
    @Autowired
    private RabbitTemplate template;

    @RabbitListener(queues = "q.user-registration")
    public void processNewUser(UserRegistrationRequest userRegistrationRequest){
        System.out.println("New User - "+userRegistrationRequest);
        template.convertAndSend("x.post-registration","",userRegistrationRequest);
    }
    @RabbitListener(queues = "q.send-sms")
    public void sendSms(UserRegistrationRequest userRegistrationRequest){
        System.out.println("Sending sms to "+userRegistrationRequest.getPhoneNo());
    }

    @RabbitListener(queues = "q.send-email")
    public void sendEmail(UserRegistrationRequest userRegistrationRequest){
        System.out.println("Sending email to "+userRegistrationRequest.getEmailId());
    }
}

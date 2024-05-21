package com.som.mq;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MQConfig {
    @Bean
    public Declarables createPostRegistrationSchema(){
        return new Declarables(new FanoutExchange("x.post-registration"),
                new Queue("q.send-email"),
                new Queue("q.send-sms"),
                new Binding("q.send-email", Binding.DestinationType.QUEUE, "x.post-registration", "send-email", null),
                new Binding("q.send-sms", Binding.DestinationType.QUEUE, "x.post-registration", "send-sms", null));
    }
@Bean
public Declarables createUserRegistrationQueue(){
    return new Declarables(new TopicExchange("x.user-registration"),
            new Queue("q.user-registration"),
            new Binding("q.user-registration", Binding.DestinationType.QUEUE, "x.user-registration","user-registration", null));
}
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory){
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}

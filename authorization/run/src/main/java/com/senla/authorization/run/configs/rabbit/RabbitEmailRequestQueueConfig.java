package com.senla.authorization.run.configs.rabbit;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitEmailRequestQueueConfig {

    @Value("${rabbitmq.communication.email.request.queue.name}")
    private String emailRequestQueueName;

    @Bean
    @Qualifier("email_request_queue")
    public Queue emailRequestQueue() {
        return new Queue(emailRequestQueueName, true);
    }

}

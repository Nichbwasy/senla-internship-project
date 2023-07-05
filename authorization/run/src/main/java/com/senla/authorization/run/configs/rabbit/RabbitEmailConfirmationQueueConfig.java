package com.senla.authorization.run.configs.rabbit;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitEmailConfirmationQueueConfig {

    @Value("${rabbitmq.communication.email.confirmation.queue.name}")
    private String emailConfirmationQueueName;

    @Bean
    @Qualifier("email_confirmation_queue")
    public Queue emailConfirmationQueue() {
        return new Queue(emailConfirmationQueueName, true);
    }

}

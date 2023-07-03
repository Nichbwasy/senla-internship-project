package com.senla.authorization.run.configs.rabbit;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitPasswordRestoreRequestQueue {

    @Value("${rabbitmq.communication.password.restore.confirmation.queue.name}")
    private String passwordRestoreConfirmationQueueName;

    @Bean
    @Qualifier("password_restore_confirmation_queue")
    public Queue passwordRestoreQueue() {
        return new Queue(passwordRestoreConfirmationQueueName, true);
    }

}

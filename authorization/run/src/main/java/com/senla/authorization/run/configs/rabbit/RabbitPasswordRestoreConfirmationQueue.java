package com.senla.authorization.run.configs.rabbit;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitPasswordRestoreConfirmationQueue {

    @Value("${rabbitmq.communication.password.restore.request.queue.name}")
    private String passwordRestoreQueueName;

    @Bean
    @Qualifier("password_restore_request_queue")
    public Queue passwordRestoreQueue() {
        return new Queue(passwordRestoreQueueName, true);
    }

}

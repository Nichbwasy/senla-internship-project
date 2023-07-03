package com.senla.authorization.service.consumer;

import com.senla.authorization.service.PasswordRestoreService;
import com.senla.authorization.service.exceptions.consumer.RabbitConsumerException;
import com.senla.common.json.JsonMapper;
import com.senla.email.dto.RestorePasswordNotificationMessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RabbitPasswordRestoreConfirmationConsumer {

    @Autowired
    private PasswordRestoreService passwordRestoreService;

    @RabbitListener(queues = "${rabbitmq.communication.password.restore.confirmation.queue.name}")
    public void passwordRestoreConfirmationListener(
            @Payload String message
    ) {
        log.info("[   RABBIT   ] Email password restore confirmation listener has received a message: {}", message);
        try {
            RestorePasswordNotificationMessageDto messageDto = JsonMapper.jsonToObject(message, RestorePasswordNotificationMessageDto.class);

            log.info("[   RABBIT   ] Trying to confirm password restoring for the '{}'....", messageDto.getEmail());
            passwordRestoreService.changePassword(messageDto);
        } catch (Exception e) {
            log.error("Exception while processing received rabbitmq message! {}", e.getMessage());
            throw new RabbitConsumerException(
                    String.format("Exception while processing received rabbitmq message! %s", e.getMessage())
            );
        }
    }

}

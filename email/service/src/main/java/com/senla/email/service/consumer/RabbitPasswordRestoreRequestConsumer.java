package com.senla.email.service.consumer;

import com.senla.common.json.JsonMapper;
import com.senla.email.dto.RestorePasswordRequestDto;
import com.senla.email.service.PasswordRestoreService;
import com.senla.email.service.exception.consumer.RabbitConsumerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RabbitPasswordRestoreRequestConsumer {

    @Autowired
    private PasswordRestoreService passwordRestoreService;

    @RabbitListener(queues = {"${rabbitmq.communication.password.restore.request.queue.name}"})
    public void passwordRestoreRequestListener(
            @Payload String message
    ) {
        log.info("[   RABBIT   ] Password restore request listener has received message: {}", message);
        try {
            RestorePasswordRequestDto requestDto = JsonMapper.jsonToObject(message, RestorePasswordRequestDto.class);

            log.info("[   RABBIT   ] Trying to create for login '{}' password restore request...", requestDto.getLogin());
            passwordRestoreService.createRestorePasswordRequest(requestDto);
        } catch (Exception e) {
            log.error("Exception while processing received rabbitmq message! {}", e.getMessage());
            throw new RabbitConsumerException(
                    String.format("Exception while processing received rabbitmq message! %s", e.getMessage())
            );
        }
    }

}

package com.senla.authorization.service.consumer;

import com.senla.authorization.service.UserEmailService;
import com.senla.authorization.service.exceptions.consumer.RabbitConsumerException;
import com.senla.common.json.JsonMapper;
import com.senla.email.dto.EmailConfirmedNotificationMessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RabbitEmailConfirmationConsumer {

    @Autowired
    private UserEmailService userEmailService;

    @RabbitListener(queues = {"${rabbitmq.communication.email.confirmation.queue.name}"})
    public void rabbitEmailConfirmationListener(
            @Payload String message
    ) {
        log.info("[   RABBIT   ] Email confirmation listener has received a message: {}", message);
        try {
            EmailConfirmedNotificationMessageDto confirmedMessageDto =
                    JsonMapper.jsonToObject(message, EmailConfirmedNotificationMessageDto.class);

            log.info("[   RABBIT   ] Trying to confirm email '{}'.", confirmedMessageDto.getEmail());
            userEmailService.confirmUserEmail(confirmedMessageDto);
        } catch (Exception e) {
            log.error("Exception while processing received rabbitmq message! {}", e.getMessage());
            throw new RabbitConsumerException(
                    String.format("Exception while processing received rabbitmq message! %s", e.getMessage())
            );
        }
    }

}

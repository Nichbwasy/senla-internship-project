package com.senla.email.service.consumer;

import com.senla.common.json.JsonMapper;
import com.senla.email.dto.MailingRequestDto;
import com.senla.email.service.MailingRequestService;
import com.senla.email.service.exception.consumer.RabbitConsumerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RabbitEmailRequestConsumer {

    @Autowired
    private MailingRequestService mailingRequestService;

    @RabbitListener(queues = {"${rabbitmq.communication.email.request.queue.name}"})
    public void rabbitMailingRequestListener(
            @Payload String message
    ) {
        log.info("[   RABBIT   ] Mailing request listener has received message: {}", message);
        try {
            MailingRequestDto requestDto = JsonMapper.jsonToObject(message, MailingRequestDto.class);

            log.info("[   RABBIT   ] Trying to create for email '{}' mailing request...", requestDto.getRecipientEmail());
            mailingRequestService.createMailingRequest(requestDto);
        } catch (Exception e) {
            log.error("Exception while processing received rabbitmq message! {}", e.getMessage());
            throw new RabbitConsumerException(
                    String.format("Exception while processing received rabbitmq message! %s", e.getMessage())
            );
        }
    }

}

package com.senla.rental.service.consumer;

import com.senla.common.json.JsonMapper;
import com.senla.payment.dto.PaymentReceiptDto;
import com.senla.rental.service.RequestsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Slf4j
@Component
public class KafkaRentalConsumer {

    @Autowired
    private RequestsService requestsService;

    @KafkaListener(topics = {"rental_payment_confirm"}, groupId = "payment_topic_group")
    public void listen(
            final @Payload String data,
            final Acknowledgment acknowledgment
    ) {
        log.info("[   KAFKA   ] Kafka has received data: {}", data);
        try {
            PaymentReceiptDto receiptDto = JsonMapper.jsonToObject(data, PaymentReceiptDto.class);
            receiptDto.

            requestsService.updateRequestStatus(formDto.getRequestId(), formDto.getNewRequestStatusId());
        } catch (Exception e) {
            log.error("Kafka rental consumer unable processing message! Returns to this later...");
            acknowledgment.nack(Duration.ofSeconds(30));
            return;
        }
        acknowledgment.acknowledge();
    }

}

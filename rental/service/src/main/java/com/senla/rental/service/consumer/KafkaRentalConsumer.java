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

    @KafkaListener(topics = {"${rental.payment.confirm.topic}"}, groupId = "${spring.kafka.consumer.group-id}")
    public void listen(
            final @Payload String data,
            final Acknowledgment acknowledgment
    ) {
        log.info("[   KAFKA   ] Kafka has received data: {}", data);
        try {
            PaymentReceiptDto receiptDto = JsonMapper.jsonToObject(data, PaymentReceiptDto.class);

            requestsService.setRequestStatusToPayed(receiptDto);

            log.info("[   KAFKA   ] Status of the request '{}' has been updated.", receiptDto.getOrderNumber());
        } catch (Exception e) {
            log.error("Kafka rental consumer unable processing message! Returns to this later...");
            acknowledgment.nack(Duration.ofSeconds(30));
            return;
        }
        acknowledgment.acknowledge();
    }

}

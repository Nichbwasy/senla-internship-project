package com.senla.payment.service.consumers;

import com.senla.common.json.JsonMapper;
import com.senla.payment.dto.PaymentRequestDto;
import com.senla.payment.service.PaymentRequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Slf4j
@Component
public class KafkaPaymentConsumer {

    @Autowired
    private PaymentRequestService paymentRequestService;

    @KafkaListener(topics = {"payment_request_topic"}, groupId = "payment_topic_group")
    public void listen(
           final @Payload String data,
           final Acknowledgment acknowledgment
    ) {
        log.info("[   KAFKA   ] Kafka has received data: {}", data);

        try {
            PaymentRequestDto request = JsonMapper.jsonToObject(data, PaymentRequestDto.class);

            PaymentRequestDto paymentRequest = paymentRequestService.createPaymentRequest(request);

            log.info("[   KAFKA   ] Request to pay for order '{}' on amount '{}' has been crated.",
                    paymentRequest.getOrderNumber(), paymentRequest.getAmount());
        } catch (Exception e) {
            log.error("Kafka payment consumer unable processing message! Returns to this later...");
            acknowledgment.nack(Duration.ofSeconds(30));
        }
        acknowledgment.acknowledge();
    }

}

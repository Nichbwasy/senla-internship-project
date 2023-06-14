package com.senla.payment.common.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaPaymentConsumer {

    @KafkaListener(topics = {"payment_topic"}, groupId = "payment_group")
    public void listen(String data) {
        log.info("[   KAFKA   ] Kafka has received data: {}", data);
    }

}

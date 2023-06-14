package com.senla.payment.service.consumers;

import com.senla.common.json.JsonMapper;
import com.senla.payment.dto.CarRentalReceiptDto;
import com.senla.payment.dto.clients.AcceptPaymentDto;
import com.senla.payment.service.CarRentalReceiptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaPaymentConsumer {

    @Autowired
    private CarRentalReceiptService carRentalReceiptService;

    @KafkaListener(topics = {"access_payment_topic"}, groupId = "payment_topic_group")
    public void listen(String data) {
        log.info("[   KAFKA   ] Kafka has received data: {}", data);

        AcceptPaymentDto acceptPaymentDto = JsonMapper.jsonToObject(data, AcceptPaymentDto.class);

        CarRentalReceiptDto carRentalReceiptDto = carRentalReceiptService.acceptPayment(acceptPaymentDto);

        log.info("[   KAFKA   ] Request '{}' has been payed: '{}'",
                carRentalReceiptDto.getRequestId(), carRentalReceiptDto);
    }

}

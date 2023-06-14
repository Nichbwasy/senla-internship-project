package com.senla.rental.service.consumer;

import com.senla.common.json.JsonMapper;
import com.senla.payment.dto.clients.SetPayedRequestStatusFormDto;
import com.senla.rental.service.RequestsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaRentalConsumer {

    @Autowired
    private RequestsService requestsService;

    @KafkaListener(topics = {"change_request_status_topic"}, groupId = "payment_topic_group")
    public void listen(String data) {
        log.info("[   KAFKA   ] Kafka has received data: {}", data);

        SetPayedRequestStatusFormDto formDto = JsonMapper.jsonToObject(data, SetPayedRequestStatusFormDto.class);

        requestsService.updateRequestStatus(formDto.getRequestId(), formDto.getNewRequestStatusId());
    }

}

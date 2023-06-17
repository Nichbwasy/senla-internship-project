package com.senla.payment.service.impl;

import com.senla.common.generators.StringGenerator;
import com.senla.common.json.JsonMapper;
import com.senla.common.kafka.KafkaProducer;
import com.senla.payment.dao.PaymentReceiptRepository;
import com.senla.payment.dao.PaymentRequestRepository;
import com.senla.payment.dto.PaymentReceiptDto;
import com.senla.payment.dto.PaymentRequestDto;
import com.senla.payment.model.PaymentReceipt;
import com.senla.payment.model.PaymentRequest;
import com.senla.payment.service.PaymentRequestService;
import com.senla.payment.service.exceptions.requests.RequestNotFoundRequestServiceException;
import com.senla.payment.service.mappers.PaymentReceiptMapper;
import com.senla.payment.service.mappers.PaymentRequestMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
public class PaymentRequestServiceImpl implements PaymentRequestService {

    @Value("${payment.requests.page.size}")
    private Integer PAYMENT_REQUESTS_PAGE_SIZE;

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private PaymentRequestRepository paymentRequestRepository;

    @Autowired
    private PaymentReceiptRepository paymentReceiptRepository;

    @Autowired
    private PaymentRequestMapper paymentRequestMapper;

    @Autowired
    private PaymentReceiptMapper paymentReceiptMapper;

    @Override
    @Transactional
    public PaymentRequestDto createPaymentRequest(PaymentRequestDto paymentRequestDto) {
        PaymentRequest paymentRequest = paymentRequestRepository.save(paymentRequestMapper.mapToModel(paymentRequestDto));
        log.info("Payment request '{}' has been created.", paymentRequest.getId());
        return paymentRequestMapper.mapToDto(paymentRequest);
    }

    @Override
    @Transactional
    public PaymentReceiptDto acceptPaymentRequest(String requestId) {

        checkIfRequestExists(requestId);

        PaymentRequest request = paymentRequestRepository.getById(requestId);

        PaymentReceipt receipt = createReceiptForRequest(request);

        sendNotificationToTheTopic(request, receipt);

        return paymentReceiptMapper.mapToDto(receipt);
    }

    private PaymentReceipt createReceiptForRequest(PaymentRequest request) {
        PaymentReceipt receipt = new PaymentReceipt(
                null,
                request.getOrderNumber(),
                StringGenerator.generateString(),
                new Timestamp(System.currentTimeMillis()),
                request.getAmount()
        );

        receipt = paymentReceiptRepository.save(receipt);
        log.info("Receipt '{}' for request '{}' has been created.", receipt.getId(), request.getId());
        return receipt;
    }

    private void sendNotificationToTheTopic(PaymentRequest request, PaymentReceipt receipt) {
        String mappedReceiptJson = JsonMapper.objectToJson(receipt);
        kafkaProducer.sendMessage(request.getResponseTopicName(), mappedReceiptJson);
        log.info("Notification of payment acceptation of request '{}' has been sent to the '{}' topic.",
                request.getId(), request.getResponseTopicName());
    }

    private void checkIfRequestExists(String requestId) {
        if (paymentRequestRepository.existsById(requestId)) {
            log.warn("Request with id '{}' wasn't be found!", requestId);
            throw new RequestNotFoundRequestServiceException(
                    String.format("Request with id '%s' wasn't be found!", requestId)
            );
        }
    }

    @Override
    public List<PaymentRequestDto> getPaymentRequestPage(Integer page) {
        List<PaymentRequestDto> requests = paymentRequestRepository
                .findAll(PageRequest.of(page, PAYMENT_REQUESTS_PAGE_SIZE))
                .stream()
                .map(pr -> paymentRequestMapper.mapToDto(pr))
                .collect(Collectors.toList());
        log.info("All '{}' request has been found at '{}' page.", requests.size(), page);
        return requests;
    }

    @Override
    public PaymentRequestDto getPayment(String id) {
        PaymentRequest paymentRequest = paymentRequestRepository.getById(id);
        log.info("Request with id '{}' has been found.", id);
        return paymentRequestMapper.mapToDto(paymentRequest);
    }

    @Override
    public Boolean existRequestWithOrderNumber(String orderNumber) {
        Boolean itExists = paymentRequestRepository.existsByOrderNumber(orderNumber);
        if (itExists) log.info("Request with order number '{}' exists.", orderNumber);
        else log.info("Request with order number '{}' NOT exists.", orderNumber);
        return itExists;
    }
}

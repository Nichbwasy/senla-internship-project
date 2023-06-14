package com.senla.rental.service.impl;

import com.senla.authorization.client.UserDataMicroserviceClient;
import com.senla.authorization.dto.UserDataDto;
import com.senla.car.client.CarMicroserviceClient;
import com.senla.car.dto.CarDto;
import com.senla.common.json.JsonMapper;
import com.senla.common.kafka.KafkaProducer;
import com.senla.payment.client.AcceptPaymentMicroserviceClient;
import com.senla.payment.dto.CarRentalReceiptDto;
import com.senla.payment.dto.clients.AcceptPaymentDto;
import com.senla.rental.dao.RequestRepository;
import com.senla.rental.dto.RequestDto;
import com.senla.rental.service.RequestsPaymentService;
import com.senla.rental.service.exceptions.payment.*;
import com.senla.rental.service.mappers.RequestMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class RequestsPaymentServiceImpl implements RequestsPaymentService {

    @Value("${access.payment.topic}")
    private String paymentTopic;

    @Autowired
    private UserDataMicroserviceClient userClient;
    @Autowired
    private CarMicroserviceClient carClient;
    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private RequestMapper requestMapper;

    @Autowired
    private KafkaProducer kafkaProducer;

    @Override
    public String payRequest(Long userId, Long requestId) {

        UserDataDto user = checkIfUserExist(userId);

        checkIfRequestExists(requestId);

        RequestDto request = checkIfRequestBelongToUser(requestId, user);

        CarDto car = checkIfCarExists(request);

        String result = paying(user, request, car);
        log.info("Request '{}' of the user '{}' has been payed.", request.getId(), user.getId());

        return result;
    }

    private String paying(UserDataDto user, RequestDto request, CarDto car) {
        AcceptPaymentDto acceptPaymentDto = new AcceptPaymentDto(user, car, request);

        String message = JsonMapper.objectToJson(acceptPaymentDto);
        kafkaProducer.sendMessage(paymentTopic, message);

        return String.format("Payment is processing for request '%s'. Please wait...", request.getId());
    }

    private CarDto checkIfCarExists(RequestDto request) {
        CarDto car = carClient.getCarById(request.getCarId());
        if (car == null) {
            log.warn("Unable find car with id '{}' for the request '{}'!", request.getCarId(), request.getCarId());
            throw new CarNotFoundPaymentException(
                    String.format("Unable find car with id '%s' for the request '%s'!",
                            request.getCarId(), request.getCarId())
            );
        }
        return car;
    }

    private RequestDto checkIfRequestBelongToUser(Long requestId, UserDataDto user) {
        RequestDto request = requestMapper.mapToDto(requestRepository.getReferenceById(requestId));
        if (!request.getUserId().equals(user.getId())) {
            log.warn("Request with id '{}' doesn't belong to the user with id '{}'!", request.getId(), user.getId());
            throw new RequestNotBelongUserPaymentException(
                    String.format("Request with id '%s' doesn't belong to the user with id '%s'!",
                            request.getId(), user.getId())
            );
        }
        return request;
    }

    private void checkIfRequestExists(Long requestId) {
        if (!requestRepository.existsById(requestId)) {
            log.warn("Request with id '{}' not found!", requestId);
            throw new RequestNotFoundPaymentException(
                    String.format("Request with id '%s' not found!", requestId)
            );
        }
    }

    private UserDataDto checkIfUserExist(Long userId) {
        UserDataDto user = userClient.getUserDataByUserId(userId);
        if (user == null) {
            log.warn("Unable find user with id '{}'!", userId);
            throw new UserNotFoundPaymentException(
                    String.format("Unable find user with id '%s'!", userId)
            );
        }
        return user;
    }
}

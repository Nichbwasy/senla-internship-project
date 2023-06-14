package com.senla.payment.service.impl;

import com.senla.authorization.client.UserDataMicroserviceClient;
import com.senla.common.constants.requests.RequestStatuses;
import com.senla.common.exception.repository.UpdateStatementRepositoryException;
import com.senla.common.json.JsonMapper;
import com.senla.common.kafka.KafkaProducer;
import com.senla.payment.dao.CarRentalReceiptRepository;
import com.senla.payment.dto.CarRentalReceiptDto;
import com.senla.payment.dto.clients.AcceptPaymentDto;
import com.senla.payment.dto.clients.SetPayedRequestStatusFormDto;
import com.senla.payment.model.CarRentalReceipt;
import com.senla.payment.service.CarRentalReceiptService;
import com.senla.payment.service.exceptions.receipts.RequestAccessingPaymentReceiptException;
import com.senla.payment.service.exceptions.receipts.RequestStatusNotFoundReceiptException;
import com.senla.payment.service.exceptions.receipts.UserNotFoundReceiptsServiceException;
import com.senla.payment.service.exceptions.receipts.UserRequestsNotFoundException;
import com.senla.payment.service.mappers.CarRentalReceiptMapper;
import com.senla.rental.client.RequestMicroserviceClient;
import com.senla.rental.client.RequestStatusMicroserviceClient;
import com.senla.rental.dto.RequestDto;
import com.senla.rental.dto.RequestStatusDto;
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
public class CarRentalReceiptServiceImpl implements CarRentalReceiptService {

    @Value("${change.request.status.topic.name}")
    private String paymentTopic;
    @Value("${car.rental.user.receipts.page.size}")
    private Integer USER_RECEIPTS_PAGE_SIZE;

    @Autowired
    private CarRentalReceiptMapper carRentalReceiptMapper;
    @Autowired
    private CarRentalReceiptRepository carRentalReceiptRepository;
    @Autowired
    private RequestMicroserviceClient requestMicroserviceClient;
    @Autowired
    private UserDataMicroserviceClient userDataMicroserviceClient;
    @Autowired
    private RequestStatusMicroserviceClient requestStatusMicroserviceClient;

    @Autowired
    private KafkaProducer kafkaProducer;

    @Override
    public CarRentalReceiptDto getCarRentalReceipt(String id) {
        CarRentalReceipt receipt = carRentalReceiptRepository.findById(id).get();
        log.info("Car refund receipt with id '{}' has been found.", id);
        return carRentalReceiptMapper.mapToDto(receipt);
    }

    @Override
    public List<CarRentalReceiptDto> getAllCarRentalReceipt() {
        List<CarRentalReceipt> receipts = carRentalReceiptRepository.findAll();
        log.info("All '{}' car refund receipts have been found.", receipts.size());
        return receipts
                .stream()
                .map(r -> carRentalReceiptMapper.mapToDto(r))
                .collect(Collectors.toList());
    }

    @Override
    public List<CarRentalReceiptDto> getUserCarRentalReceiptsPage(Long userId, Integer page) {

        checkIfUserExist(userId);

        List<RequestDto> userRequests = getUserRequests(userId);

        List<Long> requestsIds = userRequests.stream().map(RequestDto::getId).toList();
        return carRentalReceiptRepository
                .findAllByIdIn(requestsIds, PageRequest.of(page, USER_RECEIPTS_PAGE_SIZE))
                .stream()
                .map(crr -> carRentalReceiptMapper.mapToDto(crr))
                .collect(Collectors.toList());
    }

    private List<RequestDto> getUserRequests(Long userId) {
        List<RequestDto> userRequests = requestMicroserviceClient.getAllUserRequests(userId);
        if (userRequests == null) {
            log.warn("Unable to get refund request for the user with id '{}'!", userId);
            throw new UserRequestsNotFoundException(
                    String.format("Unable to get refund request for the user with id '%s'!", userId)
            );
        }
        return userRequests;
    }

    private void checkIfUserExist(Long userId) {
        if (userDataMicroserviceClient.getUserDataByUserId(userId) == null) {
            log.warn("User with id '{}' not found!", userId);
            throw new UserNotFoundReceiptsServiceException(String.format("User with id '%s' not found!", userId));
        }
    }

    @Override
    @Transactional
    public CarRentalReceiptDto acceptPayment(AcceptPaymentDto dto) {
        log.info("Rental request '{}' from user '{}' to rent the car '{}' has been accepted.",
                dto.getRequestDto().getId(), dto.getUserDataDto().getId(), dto.getCarDto().getId());

        checkRequestStatus(dto);

        RequestStatusDto requestStatus = getPayedRequestStatus();

        SetPayedRequestStatusFormDto setPayedRequestStatusFormDto =
                new SetPayedRequestStatusFormDto(dto.getRequestDto().getId(), requestStatus.getId());

        String message = JsonMapper.objectToJson(setPayedRequestStatusFormDto);
        kafkaProducer.sendMessage(paymentTopic, message);

        CarRentalReceipt carRentalReceipt = createReceipt(dto);

        log.info("Car rental receipt '{}' for the request '{}' has been saved!",
                carRentalReceipt.getId(), carRentalReceipt.getRequestId());

        return carRentalReceiptMapper.mapToDto(carRentalReceipt);
    }

    private CarRentalReceipt createReceipt(AcceptPaymentDto dto) {
        CarRentalReceipt carRentalReceipt = new CarRentalReceipt(
                null,
                dto.getUserDataDto().getId(),
                dto.getUserDataDto().getLogin(),
                dto.getRequestDto().getId(),
                dto.getCarDto().getId(),
                dto.getCarDto().getRegistration().getModel(),
                dto.getCarDto().getRegistration().getBodyNumber(),
                dto.getRequestDto().getPrice(),
                new Timestamp(System.currentTimeMillis())
        );

        carRentalReceipt = carRentalReceiptRepository.save(carRentalReceipt);
        return carRentalReceipt;
    }

    private RequestDto updateRequestStatusForRequest(AcceptPaymentDto dto, RequestStatusDto requestStatus) {
        dto.getRequestDto().setRequestStatus(requestStatus);
        RequestDto requestDto = requestMicroserviceClient.updateRequestStatus(dto.getRequestDto().getId(), requestStatus.getId());
        if (requestDto == null) {
            log.warn("Exception while updating request! Unable update request status for the request '{}'",
                    dto.getRequestDto().getId());
            throw new UpdateStatementRepositoryException(
                    String.format("Exception while updating request! Unable update request status for the request '%s'",
                            dto.getRequestDto().getId())
            );
        }
        return requestDto;
    }

    private RequestStatusDto getPayedRequestStatus() {
        RequestStatusDto requestStatus = requestStatusMicroserviceClient.getRequestStatusByName(RequestStatuses.PAYED);
        if (requestStatus == null) {
            log.warn("Unable to find request status '{}'!", RequestStatuses.PAYED);
            throw new RequestStatusNotFoundReceiptException(
                    String.format("Unable to find request status '%s'!", RequestStatuses.PAYED)
            );
        }
        return requestStatus;
    }

    private void checkRequestStatus(AcceptPaymentDto dto) {
        RequestStatusDto currentRequestStatus = dto.getRequestDto().getRequestStatus();
        if (!currentRequestStatus.getName().equals(RequestStatuses.CREATED)) {
            StringBuilder errorMessage = new StringBuilder();
            errorMessage.append(String.format("Unable accept payment for request '%s'! ", dto.getRequestDto().getId()));
            switch (currentRequestStatus.getName()) {
                case RequestStatuses.ACCEPTED, RequestStatuses.PAYED, RequestStatuses.PROCESSING ->
                        errorMessage.append("Already payed!");
                case RequestStatuses.DENIED -> errorMessage.append("Request denied payed!");
                case RequestStatuses.CLOSED -> errorMessage.append("Request closed!");
                case RequestStatuses.CANCELED -> errorMessage.append("Request canceled!");
                default -> errorMessage.append("Unknown status!");
            }
            log.warn(errorMessage.toString());
            throw new RequestAccessingPaymentReceiptException(errorMessage.toString());
        }
    }
}

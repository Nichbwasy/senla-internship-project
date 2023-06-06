package com.senla.payment.service.impl;

import com.senla.authorization.client.UserDataMicroserviceClient;
import com.senla.payment.dao.CarRentalReceiptRepository;
import com.senla.payment.dto.CarRentalReceiptDto;
import com.senla.payment.model.CarRentalReceipt;
import com.senla.payment.service.CarRentalReceiptService;
import com.senla.payment.service.exceptions.receipts.UserNotFoundReceiptsServiceException;
import com.senla.payment.service.exceptions.receipts.UserRequestsNotFoundException;
import com.senla.payment.service.mappers.CarRentalReceiptMapper;
import com.senla.rental.client.RequestMicroserviceClient;
import com.senla.rental.dto.RequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
public class CarRentalReceiptServiceImpl implements CarRentalReceiptService {

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

    @Override
    public CarRentalReceiptDto getCarRentalReceipt(Long id) {
        CarRentalReceipt receipt = carRentalReceiptRepository.getReferenceById(id);
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
}

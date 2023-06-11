package com.senla.rental.service.impl;

import com.senla.authorization.client.UserDataMicroserviceClient;
import com.senla.authorization.dto.UserDataDto;
import com.senla.common.constants.requests.RequestStatuses;
import com.senla.rental.dao.CarRefundRepository;
import com.senla.rental.dao.RequestRepository;
import com.senla.rental.dao.RequestStatusRepository;
import com.senla.rental.dto.CarRefundDto;
import com.senla.rental.dto.RefundCompensationDto;
import com.senla.rental.dto.RequestDto;
import com.senla.rental.dto.controller.UserProfileDto;
import com.senla.rental.model.CarRefund;
import com.senla.rental.model.Request;
import com.senla.rental.service.UserProfileService;
import com.senla.rental.service.exceptions.profiles.*;
import com.senla.rental.service.exceptions.requests.RequestAlreadyCanceledRequestException;
import com.senla.rental.service.mappers.CarRefundMapper;
import com.senla.rental.service.mappers.RefundCompensationMapper;
import com.senla.rental.service.mappers.RequestMapper;
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
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    private UserDataMicroserviceClient userClient;
    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private RequestStatusRepository requestStatusRepository;
    @Autowired
    private CarRefundRepository carRefundRepository;
    @Autowired
    private RequestMapper requestMapper;
    @Autowired
    private CarRefundMapper carRefundMapper;
    @Autowired
    private RefundCompensationMapper refundCompensationMapper;

    @Value("${profile.user.refunds.page.size}")
    private Integer USER_REFUNDS_PAGE_SIZE;

    @Override
    public UserProfileDto getUserProfile(Long userId) {
        log.info("Trying to get user '{}' profile...", userId);
        UserDataDto user = userClient.getUserDataByUserId(userId);

        checkIfUserExists(userId, user);

        List<RequestDto> userRequests = requestRepository
                .findAllByUserId(userId)
                .stream()
                .map(r -> requestMapper.mapToDto(r))
                .toList();
        log.info(" {} requests was found for the client '{}'.", userRequests.size(), userId);
        return new UserProfileDto(user, userRequests);
    }

    private void checkIfUserExists(Long userId, UserDataDto user) {
        if (user == null) {
            log.warn("User with id '{}' not found!", userId);
            throw new UserNotFoundProfileException(
                    String.format("User with id '%s' not found!", userId)
            );
        }
    }

    @Override
    public RequestDto showUserRequest(Long userId, Long requestId) {
        log.info("Trying to get request '{}' of user '{}'...", requestId, userId);
        UserDataDto user = userClient.getUserDataByUserId(userId);

        checkIfUserExists(userId, user);

        Request request = requestRepository.getReferenceById(requestId);

        checkIfRequestBelongToUser(user, request);

        return requestMapper.mapToDto(request);
    }

    private void checkIfRequestBelongToUser(UserDataDto user, Request request) {
        if (!request.getUserId().equals(user.getId())) {
            log.warn("Request '{}' doesn't belong to the user '{}'!", request.getId(), user.getId());
            throw new RequestNotBelongToUserProfileException(
                    String.format("Request '%s' doesn't belong to the user '%s'!", request.getId(), user.getId())
            );
        }
    }

    @Override
    @Transactional
    public Long cancelUserRequest(Long userId, Long requestId) {
        log.info("Trying to cansel request '{}' of user '{}'...", requestId, userId);
        UserDataDto user = userClient.getUserDataByUserId(userId);

        checkIfUserExists(userId, user);

        Request request = requestRepository.getReferenceById(requestId);

        checkIfRequestBelongToUser(user, request);

        checkRequestStatus(request);

        request.setRequestStatus(requestStatusRepository.findByName(RequestStatuses.CANCELED));
        log.info("Request '{}' has been canceled.", request.getId());
        return request.getId();
    }

    private void checkRequestStatus(Request request) {
        if (request.getRequestStatus().getName().equals(RequestStatuses.CLOSED) ||
                request.getRequestStatus().getName().equals(RequestStatuses.DENIED) ||
                request.getRequestStatus().getName().equals(RequestStatuses.CANCELED)) {
            log.warn("Can't cancel request '{}'! Request already resolved!", request.getId());
            throw new RequestAlreadyCanceledRequestException(
                    String.format("Can't cancel request '%s'! Request already resolved!", request.getId())
            );
        }
    }

    @Override
    public List<CarRefundDto> getAllUserRefunds(Integer page, Long userId) {
        log.info("Trying to get page '{}' all user '{}' refunds...", page, userId);
        return carRefundRepository
                .findAllByUserId(userId, PageRequest.of(page, USER_REFUNDS_PAGE_SIZE))
                .stream()
                .map(cr -> carRefundMapper.mapToDto(cr))
                .collect(Collectors.toList());
    }

    @Override
    public CarRefundDto showUserRefundDetails(Long userId, Long refundId) {
        log.info("Trying to show refund '{}' details for user '{}'...", refundId, userId);
        UserDataDto user = userClient.getUserDataByUserId(userId);

        checkIfUserExists(userId, user);

        CarRefund carRefund = carRefundRepository.getReferenceById(refundId);

        checkIfCarRefundRecordBelongUser(user, carRefund);

        return carRefundMapper.mapToDto(carRefund);
    }

    private void checkIfCarRefundRecordBelongUser(UserDataDto user, CarRefund carRefund) {
        if (!carRefund.getUserId().equals(user.getId())) {
            log.warn("Refund record '{}' doesn't belong to the user '{}'!", carRefund.getId(), user.getId());
            throw new RefundRecordNotBelongUserProfileException(
                    String.format("Refund record '%s' doesn't belong to the user '%s'!", carRefund.getId(), user.getId())
            );
        }
    }

    @Override
    public RefundCompensationDto payCompensation(Long userId, Long refundId) {
        log.info("Trying to pay refund '{}'...", refundId);
        UserDataDto user = userClient.getUserDataByUserId(userId);

        checkIfUserExists(userId, user);

        CarRefund carRefund = carRefundRepository.getReferenceById(refundId);

        checkRefundCompensation(carRefund);

        carRefund.getRefundCompensation().setIsPaid(true);
        log.info("Refund compensation '{}' has been paid!", carRefund.getId());
        return refundCompensationMapper.mapToDto(carRefund.getRefundCompensation());
    }

    private void checkRefundCompensation(CarRefund carRefund) {
        if (carRefund.getRefundCompensation() == null) {
            log.info("Compensation for refund '{}' not found!", carRefund.getId());
            throw new NoNeedCompensationProfileException(
                    String.format("Compensation for refund '%s' not found!", carRefund.getId())
            );
        }
        if (carRefund.getRefundCompensation().getIsPaid()) {
            log.info("Compensation for refund '{}' already paid!", carRefund.getId());
            throw new CompensationAlreadyPaidProfileException(
                    String.format("Compensation for refund '%s' already paid!", carRefund.getId())
            );
        }
    }
}

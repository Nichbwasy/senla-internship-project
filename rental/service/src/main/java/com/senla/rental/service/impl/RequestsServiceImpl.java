package com.senla.rental.service.impl;

import com.senla.common.constants.requests.RequestStatuses;
import com.senla.payment.dto.PaymentReceiptDto;
import com.senla.rental.dao.RequestRejectionRepository;
import com.senla.rental.dao.RequestRepository;
import com.senla.rental.dao.RequestStatusRepository;
import com.senla.rental.dto.RequestDto;
import com.senla.rental.dto.RequestRejectionDto;
import com.senla.rental.dto.controller.requests.RequestsFilterFormDto;
import com.senla.rental.model.Request;
import com.senla.rental.model.RequestRejection;
import com.senla.rental.model.RequestStatus;
import com.senla.rental.service.RequestsService;
import com.senla.rental.service.exceptions.refunds.RefundAlreadyExistsRefundException;
import com.senla.rental.service.exceptions.requests.RequestAlreadyCanceledRequestException;
import com.senla.rental.service.exceptions.requests.RequestNotFoundServiceException;
import com.senla.rental.service.exceptions.requests.statuses.*;
import com.senla.rental.service.mappers.RequestMapper;
import com.senla.rental.service.mappers.RequestRejectionMapper;
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
public class RequestsServiceImpl implements RequestsService {

    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private RequestRejectionRepository requestRejectionRepository;
    @Autowired
    private RequestStatusRepository requestStatusRepository;
    @Autowired
    private RequestRejectionMapper requestRejectionMapper;
    @Autowired
    private RequestMapper requestMapper;

    @Value("${profile.admin.requests.page.size}")
    private Integer REQUESTS_PAGE_SIZE;

    @Override
    @Transactional
    public RequestDto insert(RequestDto requestDto) {
        log.info("Trying to insert a new request record...");
        Request request = requestMapper.mapToModel(requestDto);
        return requestMapper.mapToDto(requestRepository.save(request));
    }

    @Override
    @Transactional
    public RequestDto update(RequestDto requestDto) {
        log.info("Trying to update request record with id '{}'", requestDto.getId());
        Request request = requestRepository.getReferenceById(requestDto.getId());
        requestMapper.updateModel(requestDto, request);
        return requestMapper.mapToDto(request);
    }

    @Override
    @Transactional
    public RequestDto setRequestStatusToPayed(PaymentReceiptDto dto) {

        checkIfRequestExists(dto);

        Request request = requestRepository.getByRequestOrderNumber(dto.getOrderNumber());

        checkStatusOfRequest(request);

        checkAmountAndSetNewRequestStatus(dto, request);

        return requestMapper.mapToDto(request);
    }

    private void checkAmountAndSetNewRequestStatus(PaymentReceiptDto dto, Request request) {
        if (request.getPrice().equals(dto.getAmount())) {
            RequestStatus payed = requestStatusRepository.findByName(RequestStatuses.PAYED);
            log.info("Payed amount for the request '{}' is match for receipt '{}'. Changing request status to PAYED...",
                    request.getId(), dto.getId());
            request.setRequestStatus(payed);
        } else {
            RequestStatus notPayed = requestStatusRepository.findByName(RequestStatuses.NOT_PAYED);
            log.warn("Payed amount for the request '{}' is NOT match for receipt '{}'. Changing request status to NOT PAYED...",
                    request.getId(), dto.getId());
            request.setRequestStatus(notPayed);
        }
        log.info("Status for the request '{}' has been changed.", request.getId());
    }

    private void checkStatusOfRequest(Request request) {
        switch (request.getRequestStatus().getName()) {
            case RequestStatuses.PAYED, RequestStatuses.ACCEPTED -> {
                log.warn("Can't set request status to payed! Request '{}' already payed!", request.getId());
                throw new RequestAlreadyPayedChangingStatusException(
                        String.format("Can't set request status to payed! Request '%s' already payed!",
                                request.getId())
                );
            }
            case RequestStatuses.CANCELED -> {
                log.warn("Can't set request status to payed! Request '{}' was  canceled!", request.getId());
                throw new RequestAlreadyCanceledChangingStatusException(
                        String.format("Can't set request status to payed! Request '%s' was  canceled!", request.getId())
                );
            }
            case RequestStatuses.DENIED -> {
                log.warn("Can't set request status to payed! Request '{}' was  denied!", request.getId());
                throw new RequestDeniedChangingStatusException(
                        String.format("Can't set request status to payed! Request '%s' was  denied!", request.getId())
                );
            }
            case RequestStatuses.CLOSED -> {
                log.warn("Can't set request status to payed! Request '{}' was  closed!", request.getId());
                throw new RequestClosedChangingStatusException(
                        String.format("Can't set request status to payed! Request '%s' was  closed!", request.getId())
                );
            }
        }
    }

    private void checkIfRequestExists(PaymentReceiptDto dto) {
        if (!requestRepository.existsByRequestOrderNumber(dto.getOrderNumber())) {
            log.warn("Unable find request with order number '{}'!", dto.getOrderNumber());
            throw new RequestNotFoundServiceException(
                    String.format("Unable find request with order number '%s'!", dto.getOrderNumber())
            );
        }
    }

    @Override
    public RequestDto select(Long id) {
        log.info("Trying to select request record with id '{}'...", id);
        return requestMapper.mapToDto(requestRepository.getReferenceById(id));
    }

    @Override
    @Transactional
    public Long delete(Long id) {
        log.info("Trying to delete request with id '{}'...", id);
        requestRepository.deleteById(id);
        return id;
    }

    @Override
    public List<RequestDto> selectAll() {
        log.info("Trying to select all requests records...");
        return requestRepository
                .findAll()
                .stream()
                .map(r -> requestMapper.mapToDto(r))
                .collect(Collectors.toList());
    }

    @Override
    public List<RequestDto> selectPage(Integer page, RequestsFilterFormDto requestsFilterFormDto) {
        log.info("Trying to select page '{}' with requests records...", page);
        return requestRepository
                .findAll(PageRequest.of(page, REQUESTS_PAGE_SIZE))
                .stream()
                .map(r -> requestMapper.mapToDto(r))
                .collect(Collectors.toList());
    }

    @Override
    public List<RequestDto> selectAllForUser(Long userId) {
        log.info("Trying to select all requests records for the user with id '{}'...", userId);
        return requestRepository
                .findAllByUserId(userId)
                .stream()
                .map(r -> requestMapper.mapToDto(r))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public RequestDto acceptRequest(Long requestId) {
        log.info("Trying to accept the request with id '{}'...", requestId);
        Request request = requestRepository.getReferenceById(requestId);

        checkRequestAlreadyAccepted(request);

        checkRequestAlreadyRejected(request);

        request.setRequestStatus(requestStatusRepository.findByName(RequestStatuses.ACCEPTED));
        log.info("Request '{}' has been accepted.", requestId);
        return requestMapper.mapToDto(request);
    }

    private void checkRequestAlreadyRejected(Request request) {
        if (request.getRequestStatus().getName().equals(RequestStatuses.CANCELED) ||
                request.getRequestStatus().getName().equals(RequestStatuses.CLOSED) ||
                request.getRequestStatus().getName().equals(RequestStatuses.DENIED)) {
            log.info("Request '{}' closed or canceled!", request.getId());
            throw new RequestAlreadyCanceledRequestException(
                    String.format("Request '%s' closed or canceled!", request.getId())
            );
        }
    }

    private void checkRequestAlreadyAccepted(Request request) {
        if (request.getRequestStatus().getName().equals(RequestStatuses.ACCEPTED)) {
            log.info("Request '{}' already accepted!", request.getId());
            throw new RefundAlreadyExistsRefundException(
                    String.format(" '%s' already accepted!", request.getId())
            );
        }
    }

    @Override
    @Transactional
    public RequestDto rejectRequest(Long requestId, RequestRejectionDto rejectionDto) {
        log.info("Trying to reject request with id '{}'...", requestId);
        Request request = requestRepository.getReferenceById(requestId);

        checkRequestAlreadyRejected(request);

        RequestRejection rejection = requestRejectionRepository.save(requestRejectionMapper.mapToModel(rejectionDto));
        request.setRequestStatus(requestStatusRepository.findByName(RequestStatuses.DENIED));
        request.setRequestRejection(rejection);
        log.info("Request with id '{}' has been rejected.", requestId);
        return requestMapper.mapToDto(request);
    }
}

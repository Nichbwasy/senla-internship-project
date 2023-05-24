package com.senla.rental.service.impl;

import com.senla.authorization.client.UserDataMicroserviceClient;
import com.senla.authorization.dto.UserDataDto;
import com.senla.car.client.CarMicroserviceClient;
import com.senla.car.dto.CarDto;
import com.senla.rental.common.consts.RequestStatuses;
import com.senla.rental.dao.CarRefundRepository;
import com.senla.rental.dao.RequestRepository;
import com.senla.rental.dao.RequestStatusRepository;
import com.senla.rental.dao.specifications.CarRefundSpecificationExecutor;
import com.senla.rental.dto.CarRefundDto;
import com.senla.rental.dto.controller.CreateCarRefundFormDto;
import com.senla.rental.model.CarRefund;
import com.senla.rental.model.RefundCompensation;
import com.senla.rental.model.Request;
import com.senla.rental.service.CarsRefundsService;
import com.senla.rental.service.exceptions.refunds.CarNotFoundRefundException;
import com.senla.rental.service.exceptions.refunds.CarRequestNotFoundRefundException;
import com.senla.rental.service.exceptions.refunds.RefundAlreadyExistsRefundException;
import com.senla.rental.service.exceptions.refunds.UserNotFoundRefundException;
import com.senla.rental.service.mappers.CarRefundMapper;
import com.senla.rental.service.mappers.RefundCompensationMapper;
import jakarta.validation.Valid;
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
public class CarsRefundsServiceImpl implements CarsRefundsService {

    @Autowired
    private CarRefundRepository carRefundRepository;
    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private RequestStatusRepository requestStatusRepository;
    @Autowired
    private CarRefundMapper carRefundMapper;
    @Autowired
    private RefundCompensationMapper refundCompensationMapper;
    @Autowired
    private CarMicroserviceClient carClient;
    @Autowired
    private UserDataMicroserviceClient userDataClient;

    @Value("${profile.admin.refunds.page.size}")
    private Integer REFUND_PAGE_SIZE;

    @Override
    @Transactional
    public CarRefundDto insert(CarRefundDto carRefundDto) {
        log.info("Trying to insert a new car refund record...");
        CarRefund carRefund = carRefundMapper.mapToModel(carRefundDto);
        return carRefundMapper.mapToDto(carRefundRepository.save(carRefund));
    }

    @Override
    @Transactional
    public CarRefundDto update(CarRefundDto carRefundDto) {
        log.info("Trying to update car refund record '{}'...", carRefundDto.getId());
        CarRefund carRefund = carRefundRepository.getReferenceById(carRefundDto.getId());
        carRefundMapper.updateModel(carRefundDto, carRefund);
        return carRefundMapper.mapToDto(carRefundRepository.save(carRefund));
    }

    @Override
    public CarRefundDto select(Long id) {
        log.info("Trying to select car refund record with id '{}'...", id);
        return carRefundMapper.mapToDto(carRefundRepository.getReferenceById(id));
    }

    @Override
    @Transactional
    public Long delete(Long id) {
        log.info("Trying to delete car refund record with id '{}'...", id);
        carRefundRepository.deleteById(id);
        return id;
    }

    @Override
    public List<CarRefundDto> selectAll() {
        log.info("Trying to get all car refunds records...");
        return carRefundRepository
                .findAll()
                .stream()
                .map(cr -> carRefundMapper.mapToDto(cr))
                .collect(Collectors.toList());
    }

    @Override
    public List<CarRefundDto> selectRefundsPage(Integer page) {
        log.info("Trying to get page '{}' with car refunds records...", page);
        return carRefundRepository
                .findAll(PageRequest.of(page, REFUND_PAGE_SIZE))
                .stream()
                .map(cr -> carRefundMapper.mapToDto(cr))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CarRefundDto createCarRefund(@Valid CreateCarRefundFormDto form) {
        checkIfRequestExist(form);

        Request request = requestRepository.getReferenceById(form.getRequestId());

        CarDto car = checkIfCarExist(request);

        UserDataDto user = checkIfUserExist(request);

        checkRefundRecordExistViaSpecification(request, car, user);

        CarRefund carRefund = createCarRefundRecord(form, request, car, user);

        return carRefundMapper.mapToDto(carRefund);
    }

    private void checkIfRequestExist(CreateCarRefundFormDto form) {
        if (!requestRepository.existsById(form.getRequestId())) {
            log.warn("Can't create car refund! Request '{}' to refund not found!", form.getRequestId());
            throw new CarRequestNotFoundRefundException(
                    String.format("Can't create car refund! Request '%s' to refund not found!", form.getRequestId())
            );
        }
    }

    private CarDto checkIfCarExist(Request request) {
        CarDto car = carClient.getCarById(request.getCarId());
        if (car == null) {
            log.warn("Can't create car refund! Car with id '{}' not found!", request.getCarId());
            throw new CarNotFoundRefundException(
                    String.format("Can't create car refund! Car with id '%s' not found!", request.getCarId())
            );
        }
        return car;
    }

    private UserDataDto checkIfUserExist(Request request) {
        UserDataDto user = userDataClient.getUserDataByUserId(request.getUserId());
        if (user == null) {
            log.warn("Can't create car refund! User with id '{}' not found!", request.getUserId());
            throw new UserNotFoundRefundException(
                    String.format("Can't create car refund! User with id '%s' not found!", request.getUserId())
            );
        }
        return user;
    }

    private void checkRefundRecordExist(Request request, CarDto car, UserDataDto user) {
        if (carRefundRepository.existsByCarIdAndUserIdAndStartUsingTimeAndEndUsingTime(
                car.getId(),
                user.getId(),
                request.getStartTime(),
                request.getEndTime()
        )) {
            log.warn("Can't create car refund! Record already exists!");
            throw new RefundAlreadyExistsRefundException("Can't create car refund! Record already exists!");
        }
    }

    private void checkRefundRecordExistViaSpecification(Request request, CarDto car, UserDataDto user) {
        if (carRefundRepository.exists(CarRefundSpecificationExecutor.alreadyRefunded(
                car.getId(),
                user.getId(),
                request.getStartTime(),
                request.getEndTime()
        ))) {
            log.warn("Can't create car refund! Record already exists!");
            throw new RefundAlreadyExistsRefundException("Can't create car refund! Record already exists!");
        }
    }

    private CarRefund createCarRefundRecord(CreateCarRefundFormDto form, Request request, CarDto car, UserDataDto user) {
        RefundCompensation compensation = null;
        if (form.getCompensation() != null) compensation = refundCompensationMapper.mapToModel(form.getCompensation());
        CarRefund carRefund = new CarRefund(
                null,
                user.getId(),
                car.getId(),
                request.getStartTime(),
                request.getEndTime(),
                new Timestamp(System.currentTimeMillis()),
                request,
                compensation
        );
        carRefund = carRefundRepository.save(carRefund);
        request.setRequestStatus(requestStatusRepository.findByName(RequestStatuses.CLOSED));
        log.info("New car refund record has been created.");
        return carRefund;
    }
}

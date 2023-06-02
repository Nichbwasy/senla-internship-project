package com.senla.rental.service.impl;

import com.senla.car.client.CarMicroserviceClient;
import com.senla.car.common.consts.CarStatuses;
import com.senla.car.dto.CarDto;
import com.senla.car.dto.controllers.CarsCatalogFilterForm;
import com.senla.common.formaters.TimestampFormatter;
import com.senla.rental.common.consts.RequestStatuses;
import com.senla.rental.dao.RequestRepository;
import com.senla.rental.dao.RequestStatusRepository;
import com.senla.rental.dto.RequestDto;
import com.senla.rental.dto.controller.CarOrderingRequestDto;
import com.senla.rental.model.Request;
import com.senla.rental.service.CatalogService;
import com.senla.rental.service.exceptions.catalog.CarAlreadyOrderedCatalogException;
import com.senla.rental.service.exceptions.catalog.CarNotFoundCatalogException;
import com.senla.rental.service.exceptions.catalog.CarUnavailableCatalogException;
import com.senla.rental.service.mappers.RequestMapper;
import com.senla.starter.jwt.security.utils.utils.JwtTokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
public class CatalogServiceImpl implements CatalogService {

    @Autowired
    private CarMicroserviceClient carClient;
    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private RequestStatusRepository requestStatusRepository;
    @Autowired
    private JwtTokenUtils jwtTokenUtils;
    @Autowired
    private RequestMapper requestMapper;

    @Override
    public List<CarDto> showAllFilteredCars(Integer page, CarsCatalogFilterForm catalogFilterForm) {
        log.info("Trying to get '{}' page of cars catalog...", page);
        return carClient.getFilteredCars(page, catalogFilterForm);
    }

    @Override
    public RequestDto createCarOrderingRequest(String accessToken, CarOrderingRequestDto form) {
        log.info("Trying to create order request for the car '{}'...", form.getCarId());
        CarDto car = carClient.getCarById(form.getCarId());

        checkIfCarExists(form, car);

        checkIfCarAvailableToOrder(car);

        checkIfCarAlreadyWasOrdered(form, car);

        return reserveCar(accessToken, form, car);
    }

    private void checkIfCarAlreadyWasOrdered(CarOrderingRequestDto form, CarDto car) {
        if (checkCarAlreadyOrdered(car.getId(), form.getStartTime(), form.getEndTimme())) {
            log.warn("Can't order the car '{}'! Car already ordered at this time!", car.getId());
            throw new CarAlreadyOrderedCatalogException(
                    String.format("Can't order the car '%s'! Car already ordered at this time!", car.getId())
            );
        }
    }

    private void checkIfCarAvailableToOrder(CarDto car) {
        if (!car.getStatus().getName().equals(CarStatuses.AVAILABLE)) {
            log.warn("Can't order the car '{}'! Car unavailable!", car.getId());
            throw new CarUnavailableCatalogException(
                    String.format("Can't order the car '%s'! Car unavailable!", car.getId())
            );
        }
    }

    private void checkIfCarExists(CarOrderingRequestDto form, CarDto car) {
        if (car == null) {
            log.warn("Can't order the car! Car with id '{}' not found!", form.getCarId());
            throw new CarNotFoundCatalogException(
                    String.format("Can't order the car! Car with id '%s' not found!", form.getCarId())
            );
        }
    }

    private RequestDto reserveCar(String accessToken, CarOrderingRequestDto orderingDto, CarDto car) {
        Long userId = jwtTokenUtils.getAccessTokenUserId(accessToken);
        Request request = new Request();
        request.setCarId(car.getId());
        request.setUserId(userId);
        request.setStartTime(orderingDto.getStartTime());
        request.setEndTime(orderingDto.getEndTimme());
        request.setRequestStatus(requestStatusRepository.findByName(RequestStatuses.CREATED));
        request = requestRepository.save(request);
        log.info("Car '{}' has been reserved by user '{}' from '{}' to '{}'.",
                car.getId(),
                userId,
                TimestampFormatter.formatToString(orderingDto.getStartTime()),
                TimestampFormatter.formatToString(orderingDto.getEndTimme())
        );
        return requestMapper.mapToDto(request);
    }

    private Boolean checkCarAlreadyOrdered(Long carId, Timestamp start, Timestamp end) {
        List<Request> requestForTheSameCar = requestRepository.findAllByCarId(carId);
        long rs = start.getTime();
        long re = end.getTime();
        return requestForTheSameCar.stream()
                .anyMatch(
                        r -> {
                            long cs = r.getStartTime().getTime();
                            long ce = r.getEndTime().getTime();
                            return (cs <= rs  && ce >= re) ||
                                    ( (rs <= cs && cs <= re) || (re >= ce && ce >= rs) );
                        }
                );
    }
}

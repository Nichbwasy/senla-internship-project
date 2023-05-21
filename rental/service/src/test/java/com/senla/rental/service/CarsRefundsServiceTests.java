package com.senla.rental.service;

import com.senla.authorization.client.UserDataMicroserviceClient;
import com.senla.authorization.dto.UserDataDto;
import com.senla.car.client.CarMicroserviceClient;
import com.senla.car.dto.CarDto;
import com.senla.common.exception.repository.EntityNotFoundException;
import com.senla.common.exception.repository.InsertStatementRepositoryException;
import com.senla.rental.common.consts.RequestStatuses;
import com.senla.rental.dao.CarRefundRepository;
import com.senla.rental.dao.RequestRepository;
import com.senla.rental.dao.RequestStatusRepository;
import com.senla.rental.dto.CarRefundDto;
import com.senla.rental.dto.RefundCompensationDto;
import com.senla.rental.dto.controller.CreateCarRefundFormDto;
import com.senla.rental.model.CarRefund;
import com.senla.rental.model.Request;
import com.senla.rental.model.RequestStatus;
import com.senla.rental.service.exceptions.refunds.CarNotFoundRefundException;
import com.senla.rental.service.exceptions.refunds.CarRequestNotFoundRefundException;
import com.senla.rental.service.exceptions.refunds.RefundAlreadyExistsRefundException;
import com.senla.rental.service.exceptions.refunds.UserNotFoundRefundException;
import com.senla.rental.service.impl.CarsRefundsServiceImpl;
import com.senla.rental.service.mappers.CarRefundMapper;
import com.senla.rental.service.mappers.RefundCompensationMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
public class CarsRefundsServiceTests {

    @Spy
    private final RefundCompensationMapper refundCompensationMapper = Mappers.getMapper(RefundCompensationMapper.class);
    @Spy
    private final CarRefundMapper carRefundMapper = Mappers.getMapper(CarRefundMapper.class);
    @Mock
    private RequestRepository requestRepository;
    @Mock
    private RequestStatusRepository requestStatusRepository;
    @Mock
    private UserDataMicroserviceClient userDataClient;
    @Mock
    private CarMicroserviceClient carClient;

    @Mock
    private CarRefundRepository carRefundRepository;

    @InjectMocks
    private CarsRefundsService carsRefundsService = new CarsRefundsServiceImpl();

    @Test
    public void insertTest() {
        CarRefundDto carRefundDto = new CarRefundDto(
                1L, 
                1L, 
                1L, 
                new Timestamp(System.currentTimeMillis()), 
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                null,
                null
        );
        Mockito.lenient()
                .when(carRefundRepository.save(Mockito.any(CarRefund.class)))
                .thenAnswer(a -> a.getArgument(0));

        Assertions.assertEquals(carRefundDto, carsRefundsService.insert(carRefundDto));
    }

    @Test
    public void insertNullTest() {
        Mockito.lenient()
                .when(carRefundRepository.save(Mockito.nullable(CarRefund.class)))
                .thenThrow(InsertStatementRepositoryException.class);

        Assertions.assertThrows(InsertStatementRepositoryException.class,
                () -> carsRefundsService.insert(null));
    }

    @Test
    public void updateTest() {
        CarRefundDto carRefundDto = new CarRefundDto(
                1L, 
                1L, 
                1L, 
                new Timestamp(System.currentTimeMillis()), 
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                        null,
                null
        );
        CarRefund carRefund = carRefundMapper.mapToModel(carRefundDto);

        Mockito.lenient()
                .when(carRefundRepository.getReferenceById(carRefundDto.getId()))
                .thenReturn(carRefund);
        Mockito.lenient()
                .when(carRefundRepository.save(Mockito.any(CarRefund.class)))
                .thenAnswer(a -> a.getArgument(0));

        Assertions.assertEquals(carRefundDto, carsRefundsService.update(carRefundDto));
    }

    @Test
    public void updateNotExistedTest() {
        CarRefundDto carRefundDto = new CarRefundDto(
                2L,
                2L,
                2L,
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                null,
                null
        );
        Mockito.when(carRefundRepository.getReferenceById(carRefundDto.getId()))
                .thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class, () -> carsRefundsService.update(carRefundDto));
    }

    @Test
    public void updateNullTest() {
        Mockito.lenient()
                .when(carRefundRepository.getReferenceById(Mockito.nullable(Long.class)))
                .thenThrow(NullPointerException.class);

        Assertions.assertThrows(NullPointerException.class,
                () -> carsRefundsService.update(null));
    }

    @Test
    public void deleteTest() {
        Mockito.lenient().doNothing().when(carRefundRepository).deleteById(Mockito.anyLong());

        Assertions.assertEquals(1, carsRefundsService.delete(1L));
    }

    @Test
    public void deleteNotExistedTest() {
        Mockito.lenient()
                .doNothing().when(carRefundRepository).deleteById(Mockito.anyLong());

        Assertions.assertDoesNotThrow(() -> carsRefundsService.delete(1L));
    }

    @Test
    public void deleteNullTest() {
        Mockito.lenient()
                .doThrow(NullPointerException.class)
                .when(carRefundRepository).deleteById(Mockito.nullable(Long.class));

        Assertions.assertThrows(NullPointerException.class,
                () -> carsRefundsService.delete(null));
    }

    @Test
    public void selectTest() {
        CarRefund carRefund = new CarRefund(
                1L,
                1L,
                1L,
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                null,
                null
        );
        Mockito.when(carRefundRepository.getReferenceById(1L)).thenReturn(carRefund);

        CarRefundDto carRefundDto = carsRefundsService.select(1L);
        Assertions.assertEquals(carRefundDto, carRefundMapper.mapToDto(carRefund));
    }

    @Test
    public void selectNotExistedTest() {
        Mockito.lenient()
                .when(carRefundRepository.getReferenceById(Mockito.anyLong()))
                .thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> carsRefundsService.select(1L));
    }

    @Test
    public void selectNullTest() {
        Mockito.lenient()
                .when(carRefundRepository.getReferenceById(Mockito.nullable(Long.class)))
                .thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> carsRefundsService.select(null));
    }

    @Test
    public void selectAllTest() {
        List<CarRefund> carRefunds = new ArrayList<>();
        carRefunds.add(new CarRefund(
                1L,
                1L,
                1L,
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                null,
                null
        ));
        carRefunds.add(new CarRefund(
                2L,
                2L,
                2L,
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                null,
                null
        ));

        Mockito.lenient().when(carRefundRepository.findAll()).thenReturn(carRefunds);

        List<CarRefundDto> carRefundDtos = carsRefundsService.selectAll();
        Assertions.assertEquals(2, carRefundDtos.size());
        for (int i = 0; i < carRefundDtos.size(); i++) {
            Assertions.assertEquals(carRefundDtos.get(i), carRefundMapper.mapToDto(carRefunds.get(i)));
        }
    }

    @Test
    public void createCarRefundTest() {
        RefundCompensationDto compensationDto = new RefundCompensationDto(
                1L,
                "Title",
                "Text",
                false,
                100D
        );
        Timestamp time = new Timestamp(1);
        CreateCarRefundFormDto form = new CreateCarRefundFormDto(1L, compensationDto);
        CarDto carDto = new CarDto(1L, "desc", 100D, null, null, null);
        UserDataDto userDataDto = new UserDataDto(1L, "login", null);
        RequestStatus requestStatus = new RequestStatus(1L, "CLOSED");
        Request request = new Request(1L, 1L, 1L, time, time, null, null);

        Mockito.when(requestRepository.existsById(1L)).thenReturn(true);
        Mockito.when(requestRepository.getReferenceById(1L)).thenReturn(request);
        Mockito.when(carClient.getCarById(1L)).thenReturn(carDto);
        Mockito.when(userDataClient.getUserDataByUserId(1L)).thenReturn(userDataDto);
        Mockito.when(carRefundRepository.existsByCarIdAndUserIdAndStartUsingTimeAndEndUsingTime(
                1L, 1L, time, time
        )).thenReturn(false);
        Mockito.when(carRefundRepository.save(Mockito.any()))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());
        Mockito.when(requestStatusRepository.findByName(RequestStatuses.CLOSED)).thenReturn(requestStatus);

        CarRefundDto result = carsRefundsService.createCarRefund(form);
        Assertions.assertEquals(carDto.getId(), result.getCarId());
        Assertions.assertEquals(userDataDto.getId(), result.getUserId());
        Assertions.assertEquals(request.getStartTime(), result.getStartUsingTime());
        Assertions.assertEquals(request.getEndTime(), result.getEndUsingTime());
    }

    @Test
    public void createCarRefundIfRefundRecordDoesntExistsTest() {
        RefundCompensationDto compensationDto = new RefundCompensationDto(
                1L,
                "Title",
                "Text",
                false,
                100D
        );
        Timestamp time = new Timestamp(1);
        CreateCarRefundFormDto form = new CreateCarRefundFormDto(1L, compensationDto);
        CarDto carDto = new CarDto(1L, "desc", 100D, null, null, null);
        UserDataDto userDataDto = new UserDataDto(1L, "login", null);
        Request request = new Request(1L, 1L, 1L, time, time, null, null);

        Mockito.when(requestRepository.existsById(1L)).thenReturn(true);
        Mockito.when(requestRepository.getReferenceById(1L)).thenReturn(request);
        Mockito.when(carClient.getCarById(1L)).thenReturn(carDto);
        Mockito.when(userDataClient.getUserDataByUserId(1L)).thenReturn(userDataDto);
        Mockito.lenient().when(carRefundRepository.existsByCarIdAndUserIdAndStartUsingTimeAndEndUsingTime(
                Mockito.anyLong(), Mockito.anyLong(), Mockito.any(Timestamp.class), Mockito.any(Timestamp.class)
        )).thenReturn(true);

        Assertions.assertThrows(RefundAlreadyExistsRefundException.class, () -> carsRefundsService.createCarRefund(form));
    }

    @Test
    public void createCarRefundIfUserDataRecordDoesntExistsTest() {
        RefundCompensationDto compensationDto = new RefundCompensationDto(
                1L,
                "Title",
                "Text",
                false,
                100D
        );
        Timestamp time = new Timestamp(1);
        CreateCarRefundFormDto form = new CreateCarRefundFormDto(1L, compensationDto);
        CarDto carDto = new CarDto(1L, "desc", 100D, null, null, null);
        Request request = new Request(1L, 1L, 1L, time, time, null, null);

        Mockito.when(requestRepository.existsById(1L)).thenReturn(true);
        Mockito.when(requestRepository.getReferenceById(1L)).thenReturn(request);
        Mockito.when(carClient.getCarById(1L)).thenReturn(carDto);
        Mockito.when(userDataClient.getUserDataByUserId(Mockito.anyLong())).thenReturn(null);

        Assertions.assertThrows(UserNotFoundRefundException.class, () -> carsRefundsService.createCarRefund(form));
    }

    @Test
    public void createCarRefundIfCarRecordDoesntExistsTest() {
        RefundCompensationDto compensationDto = new RefundCompensationDto(
                1L,
                "Title",
                "Text",
                false,
                100D
        );
        Timestamp time = new Timestamp(1);
        CreateCarRefundFormDto form = new CreateCarRefundFormDto(1L, compensationDto);
        Request request = new Request(1L, 1L, 1L, time, time, null, null);

        Mockito.when(requestRepository.existsById(1L)).thenReturn(true);
        Mockito.when(requestRepository.getReferenceById(1L)).thenReturn(request);
        Mockito.when(carClient.getCarById(1L)).thenReturn(null);

        Assertions.assertThrows(CarNotFoundRefundException.class, () -> carsRefundsService.createCarRefund(form));
    }

    @Test
    public void createCarRefundIfRequestRecordDoesntExistsTest() {
        RefundCompensationDto compensationDto = new RefundCompensationDto(
                1L,
                "Title",
                "Text",
                false,
                100D
        );
        CreateCarRefundFormDto form = new CreateCarRefundFormDto(1L, compensationDto);

        Mockito.when(requestRepository.existsById(1L)).thenReturn(false);

        Assertions.assertThrows(CarRequestNotFoundRefundException.class, () -> carsRefundsService.createCarRefund(form));
    }

    @Test
    public void createCarRefundIfFormIsNullTest() {
        Mockito.when(requestRepository.existsById(Mockito.nullable(Long.class))).thenThrow(NullPointerException.class);

        Assertions.assertThrows(NullPointerException.class, () -> carsRefundsService.createCarRefund(null));
    }

}

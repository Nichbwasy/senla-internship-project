package com.senla.rental.service;

import com.senla.common.exception.repository.EntityNotFoundException;
import com.senla.common.exception.repository.InsertStatementRepositoryException;
import com.senla.rental.common.consts.RequestStatuses;
import com.senla.rental.dao.RefundCompensationRepository;
import com.senla.rental.dao.RequestRejectionRepository;
import com.senla.rental.dao.RequestRepository;
import com.senla.rental.dao.RequestStatusRepository;
import com.senla.rental.dto.RequestDto;
import com.senla.rental.dto.RequestRejectionDto;
import com.senla.rental.model.Request;
import com.senla.rental.model.RequestRejection;
import com.senla.rental.model.RequestStatus;
import com.senla.rental.service.exceptions.refunds.RefundAlreadyExistsRefundException;
import com.senla.rental.service.exceptions.requests.RequestAlreadyCanceledRequestException;
import com.senla.rental.service.impl.RequestsServiceImpl;
import com.senla.rental.service.mappers.RefundCompensationMapper;
import com.senla.rental.service.mappers.RequestMapper;
import com.senla.rental.service.mappers.RequestRejectionMapper;
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
public class RequestsServiceTests {

    @InjectMocks
    private RequestsService requestsService = new RequestsServiceImpl();
    @Spy
    private RequestMapper requestMapper = Mappers.getMapper(RequestMapper.class);
    @Spy
    private RequestRejectionMapper requestRejectionMapper = Mappers.getMapper(RequestRejectionMapper.class);
    @Mock
    private RequestRepository requestsRepository;
    @Mock
    private RefundCompensationRepository refundCompensationsRepository;
    @Mock
    private RequestRejectionRepository requestRejectionRepository;
    @Mock
    private RequestStatusRepository requestStatusRepository;


    @Test
    public void insertTest() {
        RequestDto requestDto = new RequestDto(
                1L,
                1L,
                1L,
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                null,
                null
        );
        Mockito.lenient()
                .when(requestsRepository.save(Mockito.any(Request.class)))
                .thenAnswer(a -> a.getArgument(0));

        Assertions.assertEquals(requestDto, requestsService.insert(requestDto));
    }

    @Test
    public void insertNullTest() {
        Mockito.lenient()
                .when(requestsRepository.save(Mockito.nullable(Request.class)))
                .thenThrow(InsertStatementRepositoryException.class);

        Assertions.assertThrows(InsertStatementRepositoryException.class,
                () -> requestsService.insert(null));
    }

    @Test
    public void updateTest() {
        RequestDto requestDto = new RequestDto(
                1L,
                1L,
                1L,
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                null,
                null
        );
        Request request = requestMapper.mapToModel(requestDto);

        Mockito.when(requestsRepository.getReferenceById(requestDto.getId()))
                        .thenReturn(request);
        Mockito.lenient()
                .when(requestsRepository.save(Mockito.any(Request.class)))
                .thenAnswer(a -> a.getArgument(0));

        Assertions.assertEquals(requestDto, requestsService.update(requestDto));
    }

    @Test
    public void updateNotExistedTest() {
        RequestDto requestDto = new RequestDto(
                1L,
                1L,
                1L,
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                null,
                null
        );
        Mockito.when(requestsRepository.getReferenceById(Mockito.any(Long.class)))
                .thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class, () -> requestsService.update(requestDto));
    }

    @Test
    public void updateNullTest() {
        Mockito.lenient()
                .when(requestsRepository.getReferenceById(Mockito.nullable(Long.class)))
                .thenThrow(NullPointerException.class);

        Assertions.assertThrows(NullPointerException.class,
                () -> requestsService.update(null));
    }

    @Test
    public void deleteTest() {
        Mockito.lenient().doNothing().when(requestsRepository).deleteById(1L);

        Assertions.assertEquals(1, requestsService.delete(1L));
    }

    @Test
    public void deleteNotExistedTest() {
        Mockito.lenient()
                .doThrow(EntityNotFoundException.class)
                .when(requestsRepository).deleteById(Mockito.anyLong());

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> requestsService.delete(1L));
    }

    @Test
    public void deleteNullTest() {
        Mockito.lenient()
                .doThrow(NullPointerException.class)
                .when(requestsRepository).deleteById(Mockito.nullable(Long.class));

        Assertions.assertThrows(NullPointerException.class,
                () -> requestsService.delete(null));
    }

    @Test
    public void selectTest() {
        Request type = new Request(
                1L,
                1L,
                1L,
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                null,
                null
        );
        Mockito.when(requestsRepository.getReferenceById(1L)).thenReturn(type);

        RequestDto requestDto = requestsService.select(1L);
        Assertions.assertEquals(requestDto, requestMapper.mapToDto(type));
    }

    @Test
    public void selectNotExistedTest() {
        Mockito.lenient()
                .when(requestsRepository.getReferenceById(Mockito.anyLong()))
                .thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> requestsService.select(1L));
    }

    @Test
    public void selectNullTest() {
        Mockito.lenient()
                .when(requestsRepository.getReferenceById(Mockito.nullable(Long.class)))
                .thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> requestsService.select(null));
    }

    @Test
    public void selectAllTest() {
        List<Request> requests = new ArrayList<>();
        requests.add(new Request(
                1L,
                1L,
                1L,
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                null,
                null
        ));
        requests.add(new Request(
                2L,
                2L,
                2L,
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                null,
                null
        ));

        Mockito.lenient().when(requestsRepository.findAll()).thenReturn(requests);

        List<RequestDto> requestDtos = requestsService.selectAll();
        Assertions.assertEquals(2, requestDtos.size());
        for (int i = 0; i < requestDtos.size(); i++) {
            Assertions.assertEquals(requestDtos.get(i), requestMapper.mapToDto(requests.get(i)));
        }
    }

    @Test
    public void acceptRequestTest() {
        Timestamp time = new Timestamp(1);
        RequestStatus status = new RequestStatus(1L, RequestStatuses.PROCESSING);
        RequestStatus statusAccepted = new RequestStatus(2L, RequestStatuses.ACCEPTED);
        Request request = new Request(1L, 1L, 1L, time, time, null, status);

        Mockito.when(requestsRepository.getReferenceById(1L)).thenReturn(request);
        Mockito.when(requestStatusRepository.findByName(RequestStatuses.ACCEPTED)).thenReturn(statusAccepted);

        RequestDto result = requestsService.acceptRequest(1L);
        Assertions.assertEquals(statusAccepted.getName(), result.getRequestStatus().getName());
    }

    @Test
    public void acceptAlreadyRejectedRequestTest() {
        Timestamp time = new Timestamp(1);
        RequestStatus status = new RequestStatus(1L, RequestStatuses.CANCELED);
        Request request = new Request(1L, 1L, 1L, time, time, null, status);

        Mockito.when(requestsRepository.getReferenceById(1L)).thenReturn(request);

        Assertions.assertThrows(RequestAlreadyCanceledRequestException.class, () -> requestsService.acceptRequest(1L));
    }

    @Test
    public void acceptAlreadyAcceptedRequestTest() {
        Timestamp time = new Timestamp(1);
        RequestStatus status = new RequestStatus(1L, RequestStatuses.ACCEPTED);
        Request request = new Request(1L, 1L, 1L, time, time, null, status);

        Mockito.when(requestsRepository.getReferenceById(1L)).thenReturn(request);

        Assertions.assertThrows(RefundAlreadyExistsRefundException.class, () -> requestsService.acceptRequest(1L));
    }

    @Test
    public void acceptNotExistedRequestTest() {
        Mockito.when(requestsRepository.getReferenceById(Mockito.anyLong())).thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class, () -> requestsService.acceptRequest(1L));
    }

    @Test
    public void acceptNullableRequestTest() {
        Mockito.when(requestsRepository.getReferenceById(Mockito.nullable(Long.class))).thenThrow(NullPointerException.class);

        Assertions.assertThrows(NullPointerException.class, () -> requestsService.acceptRequest(1L));
    }

    @Test
    public void rejectRequestTest() {
        Timestamp time = new Timestamp(1);
        RequestStatus status = new RequestStatus(1L, RequestStatuses.PROCESSING);
        RequestStatus statusDenied = new RequestStatus(2L, RequestStatuses.DENIED);
        Request request = new Request(1L, 1L, 1L, time, time, null, status);
        RequestRejectionDto rejection = new RequestRejectionDto(1L, "title", "text");

        Mockito.when(requestsRepository.getReferenceById(1L)).thenReturn(request);
        Mockito.when(requestRejectionRepository.save(Mockito.any())).thenAnswer(AdditionalAnswers.returnsFirstArg());
        Mockito.when(requestStatusRepository.findByName(RequestStatuses.DENIED)).thenReturn(statusDenied);

        RequestDto result = requestsService.rejectRequest(1L, rejection);
        Assertions.assertEquals(statusDenied.getName(), result.getRequestStatus().getName());
        Assertions.assertEquals(rejection.getId(), result.getRequestRejection().getId());
        Assertions.assertEquals(rejection.getTitle(), result.getRequestRejection().getTitle());
        Assertions.assertEquals(rejection.getText(), result.getRequestRejection().getText());
    }

    @Test
    public void rejectAlreadyRejectedRequestTest() {
        Timestamp time = new Timestamp(1);
        RequestStatus status = new RequestStatus(1L, RequestStatuses.DENIED);
        Request request = new Request(1L, 1L, 1L, time, time, null, status);
        RequestRejectionDto rejection = new RequestRejectionDto(1L, "title", "text");

        Mockito.when(requestsRepository.getReferenceById(1L)).thenReturn(request);


        Assertions.assertThrows(RequestAlreadyCanceledRequestException.class, () -> requestsService.rejectRequest(1L, rejection));
    }

    @Test
    public void rejectNotExistedRequestTest() {
        Mockito.when(requestsRepository.getReferenceById(Mockito.anyLong())).thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class, () -> requestsService.rejectRequest(1L, new RequestRejectionDto()));
    }

    @Test
    public void rejectNullableRequestTest() {
        Mockito.when(requestsRepository.getReferenceById(Mockito.nullable(Long.class))).thenThrow(NullPointerException.class);

        Assertions.assertThrows(NullPointerException.class, () -> requestsService.rejectRequest(null, null));
    }

}

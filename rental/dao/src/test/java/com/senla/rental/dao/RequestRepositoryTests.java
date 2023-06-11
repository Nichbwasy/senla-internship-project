package com.senla.rental.dao;

import com.senla.rental.dao.projections.RequestIdsOnly;
import com.senla.rental.model.Request;
import com.senla.rental.model.RequestRejection;
import com.senla.rental.model.RequestStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@SpringBootTest
public class RequestRepositoryTests {

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private RequestRejectionRepository requestRejectionRepository;

    @Autowired
    private RequestStatusRepository requestStatusRepository;

    @Test
    public void findAllByCarIdTestWithEntityGraph() {
        RequestRejection rejection1 = requestRejectionRepository.save(new RequestRejection(null, "Title1", "Text1"));
        RequestRejection rejection2 = requestRejectionRepository.save(new RequestRejection(null, "Title2", "Text2"));
        RequestStatus status = requestStatusRepository.save(new RequestStatus(null, "STATUS1"));
        requestRepository.save(new Request(null, 1L, 1L, new Timestamp(1), new Timestamp(2), new BigDecimal(0), rejection1, status));
        requestRepository.save(new Request(null, 1L, 1L, new Timestamp(4), new Timestamp(5), new BigDecimal(0), rejection2, status));

        List<Request> result = requestRepository.findAllByCarId(1L);

        Assertions.assertEquals(2, result.size());
        result.forEach(r -> Assertions.assertEquals(1L, r.getCarId()));
    }

    @Test
    public void findAllByUserIdTestWithEntityGraph() {
        RequestRejection rejection1 = requestRejectionRepository.save(new RequestRejection(null, "Title3", "Text3"));
        RequestRejection rejection2 = requestRejectionRepository.save(new RequestRejection(null, "Title4", "Text4"));
        RequestStatus status = requestStatusRepository.save(new RequestStatus(null, "STATUS2"));
        requestRepository.save(new Request(null, 2L, 2L, new Timestamp(1), new Timestamp(2), new BigDecimal(0), rejection1, status));
        requestRepository.save(new Request(null, 2L, 2L, new Timestamp(4), new Timestamp(5), new BigDecimal(0), rejection2, status));

        List<Request> result = requestRepository.findAllByUserId(2L);

        Assertions.assertEquals(2, result.size());
        result.forEach(r -> Assertions.assertEquals(2L, r.getUserId()));
    }

    @Test
    public void findAllByStartTimeBetweenProjectionTest() {
        requestRepository.save(new Request(null, 3L, 3L, new Timestamp(10), new Timestamp(11), new BigDecimal(0), null, null));
        requestRepository.save(new Request(null, 3L, 3L, new Timestamp(14), new Timestamp(15), new BigDecimal(0), null, null));
        requestRepository.save(new Request(null, 3L, 3L, new Timestamp(15), new Timestamp(16), new BigDecimal(0), null, null));
        requestRepository.save(new Request(null, 3L, 3L, new Timestamp(18), new Timestamp(19), new BigDecimal(0), null, null));

        List<RequestIdsOnly> result = requestRepository.findAllByStartTimeBetween(new Timestamp(11), new Timestamp(17));
        result.forEach(e -> System.out.println(String.format("Id: %s, Car id: %s, User id: %s", e.getId(), e.getCarId(), e.getUserId())));

        Assertions.assertEquals(2, result.size());
    }

    @Test
    public void findAllByStartTimeBetweenProjectionWithNativeQueryTest() {
        requestRepository.save(new Request(null, 4L, 4L, new Timestamp(20), new Timestamp(21), new BigDecimal(0), null, null));
        requestRepository.save(new Request(null, 4L, 4L, new Timestamp(24), new Timestamp(25), new BigDecimal(0), null, null));
        requestRepository.save(new Request(null, 4L, 4L, new Timestamp(25), new Timestamp(26), new BigDecimal(0), null, null));
        requestRepository.save(new Request(null, 4L, 4L, new Timestamp(28), new Timestamp(29), new BigDecimal(0), null, null));

        List<RequestIdsOnly> result = requestRepository.findAllByStartTimeBetweenNativeQuery(new Timestamp(21), new Timestamp(27));
        result.forEach(e -> System.out.println(String.format("Id: %s, Car id: %s, User id: %s", e.getId(), e.getCarId(), e.getUserId())));

        Assertions.assertEquals(2, result.size());
    }

}

package com.senla.rental.dao;

import com.senla.rental.model.Request;
import com.senla.rental.model.RequestRejection;
import com.senla.rental.model.RequestStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        requestRepository.save(new Request(null, 1L, 1L, new Timestamp(1), new Timestamp(2), rejection1, status));
        requestRepository.save(new Request(null, 1L, 1L, new Timestamp(4), new Timestamp(5), rejection2, status));

        List<Request> result = requestRepository.findAllByCarId(1L);

        Assertions.assertEquals(2, result.size());
        result.forEach(r -> Assertions.assertEquals(1L, r.getCarId()));
    }

    @Test
    public void findAllByUserIdTestWithEntityGraph() {
        RequestRejection rejection1 = requestRejectionRepository.save(new RequestRejection(null, "Title3", "Text3"));
        RequestRejection rejection2 = requestRejectionRepository.save(new RequestRejection(null, "Title4", "Text4"));
        RequestStatus status = requestStatusRepository.save(new RequestStatus(null, "STATUS2"));
        requestRepository.save(new Request(null, 2L, 2L, new Timestamp(1), new Timestamp(2), rejection1, status));
        requestRepository.save(new Request(null, 2L, 2L, new Timestamp(4), new Timestamp(5), rejection2, status));

        List<Request> result = requestRepository.findAllByUserId(2L);

        Assertions.assertEquals(2, result.size());
        result.forEach(r -> Assertions.assertEquals(2L, r.getUserId()));
    }

}

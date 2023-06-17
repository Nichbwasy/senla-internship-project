package com.senla.payment.dao;


import com.senla.payment.model.PaymentRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PaymentRequestRepository extends MongoRepository<PaymentRequest, String> {
    Boolean existsByOrderNumber(String orderNumber);
    PaymentRequest getById(String id);

}

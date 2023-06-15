package com.senla.payment.dao;


import com.senla.payment.model.PaymentRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PaymentRequestRepository extends MongoRepository<PaymentRequest, String> {

    PaymentRequest getById(String id);

}

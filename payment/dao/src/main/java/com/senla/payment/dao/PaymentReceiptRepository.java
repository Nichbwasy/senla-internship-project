package com.senla.payment.dao;

import com.senla.payment.model.PaymentReceipt;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PaymentReceiptRepository extends MongoRepository<PaymentReceipt, String> {

    PaymentReceipt findByReceiptNumber(String receiptNumber);

}

package com.senla.payment.dao;

import com.senla.payment.model.CarRentalReceipt;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CarRentalReceiptRepository extends MongoRepository<CarRentalReceipt, String> {
    List<CarRentalReceipt> findAllByIdIn(List<Long> ids, Pageable pageable);

}

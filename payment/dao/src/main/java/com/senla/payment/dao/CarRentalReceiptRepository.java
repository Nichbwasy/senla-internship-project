package com.senla.payment.dao;

import com.senla.payment.model.CarRentalReceipt;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRentalReceiptRepository extends JpaRepository<CarRentalReceipt, Long> {
    List<CarRentalReceipt> findAllByIdIn(List<Long> ids, Pageable pageable);

}

package com.senla.rental.dao;

import com.senla.rental.model.CarRefund;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRefundRepository extends JpaRepository<CarRefund, Long> {
}

package com.senla.rental.dao;

import com.senla.rental.model.CarRefund;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface CarRefundRepository extends JpaRepository<CarRefund, Long> {

    Boolean existsByCarIdAndUserIdAndStartUsingTimeAndEndUsingTime(
            Long carId,
            Long userId,
            Timestamp startTime,
            Timestamp endTime
    );

    List<CarRefund> findAllByUserId(Long userId, Pageable pageable);

}

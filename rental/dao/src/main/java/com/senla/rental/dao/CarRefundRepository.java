package com.senla.rental.dao;

import com.senla.rental.model.CarRefund;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface CarRefundRepository extends JpaRepository<CarRefund, Long>, JpaSpecificationExecutor<CarRefund> {

    Boolean existsByCarIdAndUserIdAndStartUsingTimeAndEndUsingTime(
            Long carId,
            Long userId,
            Timestamp startTime,
            Timestamp endTime
    );

    boolean exists(Specification<CarRefund> specification);

    List<CarRefund> findAllByUserId(Long userId, Pageable pageable);

}

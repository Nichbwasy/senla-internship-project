package com.senla.rental.dao.specifications;

import com.senla.rental.model.CarRefund;
import com.senla.rental.model.CarRefund_;
import org.springframework.data.jpa.domain.Specification;

import java.sql.Timestamp;

public class CarRefundSpecificationExecutor {

    public static Specification<CarRefund> alreadyRefunded(Long carId, Long userId, Timestamp startTime, Timestamp endTime) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.and(
                        criteriaBuilder.equal(root.get(CarRefund_.carId), carId),
                        criteriaBuilder.equal(root.get(CarRefund_.userId), userId),
                        criteriaBuilder.equal(root.get(CarRefund_.startUsingTime), startTime),
                        criteriaBuilder.equal(root.get(CarRefund_.endUsingTime), endTime)
                );
    }

    public static Specification<CarRefund> forPeriodOfTime(Timestamp from, Timestamp to) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.and(
                        criteriaBuilder.between(root.get(CarRefund_.startUsingTime), from, to),
                        criteriaBuilder.between(root.get(CarRefund_.endUsingTime), from, to)
                );
    }

}

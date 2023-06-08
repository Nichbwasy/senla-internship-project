package com.senla.rental.dao;

import com.senla.rental.model.RentalRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalRateRepository extends JpaRepository<RentalRate, Long> {

    RentalRate findByCarTypeAndCarCondition(String carType, String carCondition);

}

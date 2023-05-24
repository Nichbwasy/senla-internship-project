package com.senla.rental.dao;

import com.senla.rental.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findAllByCarId(Long carId);
    List<Request> findAllByUserId(Long userId);
}

package com.senla.rental.dao;

import com.senla.rental.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findAllByCarId(Long carId);
    List<Request> findAllByUserId(Long userId);
}

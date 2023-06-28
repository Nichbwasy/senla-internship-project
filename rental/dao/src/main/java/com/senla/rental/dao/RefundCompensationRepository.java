package com.senla.rental.dao;

import com.senla.rental.model.RefundCompensation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefundCompensationRepository extends JpaRepository<RefundCompensation, Long> {
}

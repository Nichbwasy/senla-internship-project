package com.senla.rental.dao;

import com.senla.rental.model.RequestRejection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRejectionRepository extends JpaRepository<RequestRejection, Long> {
}

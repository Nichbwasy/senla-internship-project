package com.senla.rental.dao;

import com.senla.rental.model.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestStatusRepository extends JpaRepository<RequestStatus, Long> {
    RequestStatus findByName(String name);
}

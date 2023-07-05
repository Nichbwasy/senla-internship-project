package com.senla.email.dao;

import com.senla.email.model.RestorePasswordRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestorePasswordRequestRepository extends JpaRepository<RestorePasswordRequest, Long> {

    Boolean existsByEmail(String email);
    RestorePasswordRequest getByEmail(String email);
    List<RestorePasswordRequest> findAllBySendingStatus(String sendingStatus, Pageable pageable);

}

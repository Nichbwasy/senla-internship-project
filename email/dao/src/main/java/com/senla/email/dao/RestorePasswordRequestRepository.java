package com.senla.email.dao;

import com.senla.email.model.RestorePasswordRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestorePasswordRequestRepository extends JpaRepository<RestorePasswordRequest, Long> {

    Boolean existsByEmail(String email);
    RestorePasswordRequest getByEmail(String email);

}

package com.senla.email.dao;

import com.senla.email.model.RestorePasswordConfirmationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestorePasswordConfirmationCodeRepository extends JpaRepository<RestorePasswordConfirmationCode, Long> {

    Boolean existsByEmail(String email);
    RestorePasswordConfirmationCode getByEmail(String email);
    Boolean existsByCode(String code);
    RestorePasswordConfirmationCode getByCode(String code);

}

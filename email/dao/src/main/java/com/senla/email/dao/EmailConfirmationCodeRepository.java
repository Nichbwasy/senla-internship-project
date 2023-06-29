package com.senla.email.dao;

import com.senla.email.model.EmailConfirmationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailConfirmationCodeRepository extends JpaRepository<EmailConfirmationCode, Long> {

    Boolean existsByRecipientEmail(String email);
    EmailConfirmationCode getByRecipientEmail(String email);

    Boolean existsByConfirmationCode(String code);
    EmailConfirmationCode getByConfirmationCode(String code);

}

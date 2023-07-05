package com.senla.email.dao;

import com.senla.email.model.MailingRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailingRequestRepository extends JpaRepository<MailingRequest, Long> {

    MailingRequest getByRecipientEmail(String email);
    Boolean existsByRecipientEmail(String email);

    void deleteByRecipientEmail(String email);

}

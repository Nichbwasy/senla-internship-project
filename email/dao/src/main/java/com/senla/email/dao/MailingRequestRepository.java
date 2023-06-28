package com.senla.email.dao;

import com.senla.email.model.MailingRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailingRequestRepository extends JpaRepository<MailingRequest, Long> {
}

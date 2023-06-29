package com.senla.email.dao;

import com.senla.email.model.ConfirmationMail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfirmationMailRepository extends JpaRepository<ConfirmationMail, Long> {
}

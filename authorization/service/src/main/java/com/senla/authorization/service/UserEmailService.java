package com.senla.authorization.service;

import com.senla.authorization.dto.UserDataDto;
import com.senla.email.dto.EmailConfirmedNotificationMessageDto;

public interface UserEmailService {

    /**
     * Verifiers user email via notification received from email microservice
     * @param messageDto Email microservice notification message
     * @return Updated user data
     */
    UserDataDto confirmUserEmail(EmailConfirmedNotificationMessageDto messageDto);

}

package com.senla.authorization.service;

import com.senla.authorization.dto.UserDataDto;
import com.senla.authorization.dto.controllers.CreatePasswordRestoreRequestDto;
import com.senla.email.dto.RestorePasswordNotificationMessageDto;

public interface PasswordRestoreService {

    /**
     * Sends rabbitmq request to the email microservice to restore user's password
     * @param dto User data to restore a password
     * @return Status message
     */
    String createRestorePasswordRequest(CreatePasswordRestoreRequestDto dto);

    /**
     * Changes user's password to a new one
     * @param dto Restoring notification message from email microservice
     * @return Updated user data
     */
    UserDataDto changePassword(RestorePasswordNotificationMessageDto dto);

}

package com.senla.authorization.service;

import com.senla.authorization.dto.UserDataDto;
import com.senla.authorization.dto.controllers.CreatePasswordRestoreRequestDto;
import com.senla.email.dto.RestorePasswordNotificationMessageDto;

public interface PasswordRestoreService {

    String createRestorePasswordRequest(CreatePasswordRestoreRequestDto dto);

    UserDataDto changePassword(RestorePasswordNotificationMessageDto dto);

}

package com.senla.email.service;

import com.senla.email.dto.RestorePasswordRequestDto;
import com.senla.email.dto.controller.NewPasswordFormDto;

public interface PasswordRestoreService {

    /**
     * Creates a new password restore request
     * @param requestDto Password restoring request dto form
     * @return Created password restoring request
     */
    RestorePasswordRequestDto createRestorePasswordRequest(RestorePasswordRequestDto requestDto);

    /**
     * Confirms a password restoring request and sends notification to the
     * authorization microservice for user with email specified in request
     * @param newPasswordFormDto Form with new password
     * @param confirmationCode Code of request
     * @return Status message
     */
    String confirmPasswordRestoring(NewPasswordFormDto newPasswordFormDto, String confirmationCode);


}

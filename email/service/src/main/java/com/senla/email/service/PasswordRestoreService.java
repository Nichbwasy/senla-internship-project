package com.senla.email.service;

import com.senla.email.dto.RestorePasswordRequestDto;
import com.senla.email.dto.controller.NewPasswordFormDto;

public interface PasswordRestoreService {

    RestorePasswordRequestDto createRestorePasswordRequest(RestorePasswordRequestDto requestDto);

    String confirmPasswordRestoring(NewPasswordFormDto newPasswordFormDto, String confirmationCode);

    void sendPasswordRestoreNotificationMail();

}

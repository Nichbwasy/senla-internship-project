package com.senla.authorization.service;

import com.senla.authorization.dto.UserDataDto;
import com.senla.authorization.dto.registrations.UserRegistrationDataDto;

public interface RegistrationService {
    UserDataDto registerUser(UserRegistrationDataDto userRegistrationDataDto);
}

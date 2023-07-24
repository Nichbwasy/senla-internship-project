package com.senla.authorization.service;

import com.senla.authorization.dto.UserDataDto;
import com.senla.authorization.dto.registrations.UserRegistrationDataDto;

public interface RegistrationService {
    /**
     * Registers a new user in the system, if login and email is unique.
     * @param userRegistrationDataDto Registration form with user data
     * @return Created user
     */
    UserDataDto registerUser(UserRegistrationDataDto userRegistrationDataDto);
}

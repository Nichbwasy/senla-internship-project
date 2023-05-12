package com.senla.authorization.service.impl;

import com.senla.authorization.dao.UserDataRepository;
import com.senla.authorization.dto.UserDataDto;
import com.senla.authorization.dto.registrations.UserRegistrationDataDto;
import com.senla.authorization.model.UserData;
import com.senla.authorization.service.RegistrationService;
import com.senla.authorization.service.encoders.PasswordEncoder;
import com.senla.authorization.service.exceptions.services.registration.LoginInUseException;
import com.senla.authorization.service.exceptions.services.registration.PasswordMatchException;
import com.senla.authorization.service.mappers.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private UserDataRepository userDataRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional
    public UserDataDto registerUser(UserRegistrationDataDto formDto) {
        log.info("Trying to register a new user...");
        checkRegisterFormData(formDto);

        UserData user = userMapper.mapRegisterFormToUser(formDto);
        user.setPassword(PasswordEncoder.encodeString(formDto.getPassword()));
        return userMapper.mapToDto(userDataRepository.save(user));
    }

    private void checkRegisterFormData(UserRegistrationDataDto formDto) {
        if (userDataRepository.existsByLogin(formDto.getLogin())) {
            log.warn("User with login '{}' already exist!", formDto.getLogin());
            throw new LoginInUseException(
                    String.format("User with login '%s' already exist!", formDto.getLogin())
            );
        }
        if (!formDto.getPassword().equals(formDto.getRepeatPassword())) {
            log.warn("Unable register user! Passwords doesn't match!");
            throw new PasswordMatchException("Unable register user! Passwords doesn't match!");
        }
    }
}

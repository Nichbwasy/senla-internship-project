package com.senla.authorization.service.impl;

import com.senla.authorization.dao.UserDataRepository;
import com.senla.authorization.dto.UserDataDto;
import com.senla.authorization.model.UserData;
import com.senla.authorization.service.UserEmailService;
import com.senla.authorization.service.exceptions.services.email.UserWithEmailNotFoundException;
import com.senla.authorization.service.mappers.UserMapper;
import com.senla.common.constants.authorization.EmailStatuses;
import com.senla.email.dto.EmailConfirmedNotificationMessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class UserEmailServiceImpl implements UserEmailService {

    @Autowired
    private UserDataRepository userDataRepository;
    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional
    public UserDataDto confirmUserEmail(EmailConfirmedNotificationMessageDto messageDto) {

        checkIfUserWithEmailExists(messageDto);

        UserData user = userDataRepository.getByEmail(messageDto.getEmail());

        user.setEmailStatus(EmailStatuses.VERIFIED);
        log.info("Email status for the user '{}' has been set as {}.", user.getLogin(), user.getEmailStatus());
        return userMapper.mapToDto(user);
    }

    private void checkIfUserWithEmailExists(EmailConfirmedNotificationMessageDto messageDto) {
        if (!userDataRepository.existsByEmail(messageDto.getEmail())) {
            log.warn("User with email '{}' not found!", messageDto.getEmail());
            throw new UserWithEmailNotFoundException(
                    String.format("User with email '%s' not found!", messageDto.getEmail())
            );
        }
    }
}

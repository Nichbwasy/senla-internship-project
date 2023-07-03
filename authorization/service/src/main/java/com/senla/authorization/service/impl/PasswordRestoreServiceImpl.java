package com.senla.authorization.service.impl;

import com.senla.authorization.dao.UserDataRepository;
import com.senla.authorization.dto.UserDataDto;
import com.senla.authorization.dto.controllers.CreatePasswordRestoreRequestDto;
import com.senla.authorization.model.UserData;
import com.senla.authorization.service.PasswordRestoreService;
import com.senla.authorization.service.encoders.PasswordEncoder;
import com.senla.authorization.service.exceptions.services.restore.UserNotFoundPasswordRestoreException;
import com.senla.authorization.service.mappers.UserMapper;
import com.senla.common.json.JsonMapper;
import com.senla.email.dto.RestorePasswordNotificationMessageDto;
import com.senla.email.dto.RestorePasswordRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@Transactional(readOnly = true)
public class PasswordRestoreServiceImpl implements PasswordRestoreService {

    @Value("${service.password.restore.request.sent.message}")
    private String requestSentMessage;

    @Autowired
    @Qualifier("password_restore_request_queue")
    private Queue passwordRestoreRequestQueue;

    @Autowired
    @Qualifier("password_restore_confirmation_queue")
    private Queue passwordRestoreConfirmationQueue;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private UserDataRepository userDataRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public String createRestorePasswordRequest(CreatePasswordRestoreRequestDto dto) {
        checkIfUserWithLoginExists(dto);

        UserData user = userDataRepository.findByLogin(dto.getLogin());

        sendRequestToEmailService(user);

        return requestSentMessage;
    }

    private void sendRequestToEmailService(UserData user) {
        RestorePasswordRequestDto requestDto = new RestorePasswordRequestDto(
                null,
                user.getLogin(),
                user.getEmail(),
                LocalDateTime.now(),
                passwordRestoreConfirmationQueue.getName()
        );
        String json = JsonMapper.objectToJson(requestDto);
        rabbitTemplate.convertAndSend(passwordRestoreRequestQueue.getName(), json);
        log.info("Email restore request for the user '{}' has been sent to email service.", user.getId());
    }

    private void checkIfUserWithLoginExists(CreatePasswordRestoreRequestDto dto) {
        if (!userDataRepository.existsByLogin(dto.getLogin())) {
            log.warn("User with login '{}' not found!", dto.getLogin());
            throw new UserNotFoundPasswordRestoreException(
                    String.format("User with login '%s' not found!", dto.getLogin())
            );
        }
    }

    @Override
    @Transactional
    public UserDataDto changePassword(RestorePasswordNotificationMessageDto dto) {

        checkIfUserWithEmailExists(dto);

        UserData user = userDataRepository.getByEmail(dto.getEmail());
        String encodedNewPassword = PasswordEncoder.encodeString(dto.getNewPassword());
        user.setPassword(encodedNewPassword);

        return userMapper.mapToDto(user);
    }

    private void checkIfUserWithEmailExists(RestorePasswordNotificationMessageDto dto) {
        if (!userDataRepository.existsByEmail(dto.getEmail())) {
            log.warn("User with email '{}' not found!", dto.getEmail());
            throw new UserNotFoundPasswordRestoreException(
                    String.format("User with email '%s' not found!", dto.getEmail())
            );
        }
    }
}


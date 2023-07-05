package com.senla.authorization.service.impl;

import com.senla.authorization.dao.UserDataRepository;
import com.senla.authorization.dto.UserDataDto;
import com.senla.authorization.dto.registrations.UserRegistrationDataDto;
import com.senla.authorization.model.UserData;
import com.senla.authorization.service.RegistrationService;
import com.senla.authorization.service.encoders.PasswordEncoder;
import com.senla.authorization.service.exceptions.services.registration.EmailInUseException;
import com.senla.authorization.service.exceptions.services.registration.LoginInUseException;
import com.senla.authorization.service.exceptions.services.registration.PasswordMatchException;
import com.senla.authorization.service.mappers.UserMapper;
import com.senla.common.constants.authorization.EmailStatuses;
import com.senla.common.json.JsonMapper;
import com.senla.email.dto.MailingRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    @Qualifier("email_request_queue")
    private Queue requestQueue;

    @Autowired
    @Qualifier("email_confirmation_queue")
    private Queue confirmationQueue;

    @Override
    @Transactional
    public UserDataDto registerUser(UserRegistrationDataDto formDto) {
        log.info("Trying to register a new user...");
        checkRegisterFormData(formDto);

        UserData user = userMapper.mapRegisterFormToUser(formDto);
        user.setPassword(PasswordEncoder.encodeString(formDto.getPassword()));
        user.setEmailStatus(EmailStatuses.NOT_VERIFIED);

        sentRequestToEmailService(formDto);

        return userMapper.mapToDto(userDataRepository.save(user));
    }

    private void sentRequestToEmailService(UserRegistrationDataDto formDto) {
        MailingRequestDto mailingRequestDto = new MailingRequestDto(
                null,
                formDto.getEmail(),
                confirmationQueue.getName()
        );
        String json = JsonMapper.objectToJson(mailingRequestDto);
        rabbitTemplate.convertAndSend(requestQueue.getName(), json);
        log.info("Request to email service has been sent: {}", json);
    }

    private void checkRegisterFormData(UserRegistrationDataDto formDto) {
        if (userDataRepository.existsByLogin(formDto.getLogin())) {
            log.warn("User with login '{}' already exist!", formDto.getLogin());
            throw new LoginInUseException(
                    String.format("User with login '%s' already exist!", formDto.getLogin())
            );
        }
        if (userDataRepository.existsByEmail(formDto.getEmail())) {
            log.warn("User with email '{}' already exist!", formDto.getEmail());
            throw new EmailInUseException(
                    String.format("User with email '%s' already exist!", formDto.getEmail())
            );
        }
        if (!formDto.getPassword().equals(formDto.getRepeatPassword())) {
            log.warn("Unable register user! Passwords doesn't match!");
            throw new PasswordMatchException("Unable register user! Passwords doesn't match!");
        }
    }
}

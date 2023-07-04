package com.senla.email.service.impl;

import com.senla.common.constants.email.RestorePasswordRequestStatuses;
import com.senla.common.generators.StringGenerator;
import com.senla.common.json.JsonMapper;
import com.senla.email.dao.RestorePasswordConfirmationCodeRepository;
import com.senla.email.dao.RestorePasswordRequestRepository;
import com.senla.email.dto.RestorePasswordNotificationMessageDto;
import com.senla.email.dto.RestorePasswordRequestDto;
import com.senla.email.dto.controller.NewPasswordFormDto;
import com.senla.email.model.RestorePasswordConfirmationCode;
import com.senla.email.model.RestorePasswordRequest;
import com.senla.email.service.PasswordRestoreService;
import com.senla.email.service.exception.consumer.restore.ConfirmationCodeNotFoundRestorePasswordException;
import com.senla.email.service.exception.consumer.restore.PasswordsNotMatchRestorePasswordException;
import com.senla.email.service.mail.EmailSender;
import com.senla.email.service.mapper.RestorePasswordRequestMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
public class PasswordRestoreServiceImpl implements PasswordRestoreService {

    @Value("${service.password.restore.success.message}")
    private String restoreSuccessMessage;

    @Value("${password.restore.mail.link}")
    private String restoreMailLink;
    @Value("${password.restore.mail.title}")
    private String restoreMailTitle;
    @Value("${password.restore.mail.text}")
    private String restoreMailText;
    @Value("${sent.password.restore.mails.count}")
    private Integer RESTORE_PASSWORD_MAILS_COUNT;
    @Autowired
    private RestorePasswordRequestRepository requestRepository;

    @Autowired
    private RestorePasswordConfirmationCodeRepository confirmationCodeRepository;

    @Autowired
    private RestorePasswordRequestMapper requestMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private EmailSender emailSender;

    @Override
    @Transactional
    public RestorePasswordRequestDto createRestorePasswordRequest(RestorePasswordRequestDto requestDto) {
        RestorePasswordRequest request = requestRepository.save(requestMapper.mapToModel(requestDto));
        log.info("Restore password request has been created: {}", request);

        createConfirmationCode(request);

        return requestMapper.mapToDto(request);
    }

    // TODO: There is a possible to generate already existed verification code. If that will happens, DB throws exception.

    private RestorePasswordConfirmationCode createConfirmationCode(RestorePasswordRequest request) {
        RestorePasswordConfirmationCode confirmationCode;
        if (confirmationCodeRepository.existsByEmail(request.getEmail())) {
            log.info("Confirmation code already exists for an email '{}'. Code will be regenerate.", request.getEmail());
            confirmationCode = confirmationCodeRepository.getByEmail(request.getEmail());
            confirmationCode.setCode(StringGenerator.generateString(64));
            confirmationCode.setRequest(request);
        } else {
            log.info("Confirmation code not exists for an email '{}'. New code will be generate.", request.getEmail());
            confirmationCode = new RestorePasswordConfirmationCode(
                    null,
                    request.getEmail(),
                    StringGenerator.generateString(64),
                    request
            );
            confirmationCodeRepository.save(confirmationCode);
        }
        log.info("Confirmation code has been generated for '{}' email.", request.getEmail());
        return confirmationCode;
    }

    @Override
    @Transactional
    public String confirmPasswordRestoring(NewPasswordFormDto newPasswordFormDto, String confirmationCode) {
        checkIfPasswordsMatch(newPasswordFormDto);

        checkIfCodeExists(confirmationCode);

        RestorePasswordConfirmationCode code = confirmationCodeRepository.getByCode(confirmationCode);

        sendNotification(newPasswordFormDto, code.getRequest());

        confirmationCodeRepository.delete(code);
        return restoreSuccessMessage;
    }

    private void checkIfCodeExists(String confirmationCode) {
        if (!confirmationCodeRepository.existsByCode(confirmationCode)) {
            log.warn("Confirmation code '{}' not found!", confirmationCode);
            throw new ConfirmationCodeNotFoundRestorePasswordException(
                    String.format("Confirmation code '%s' not found!", confirmationCode)
            );
        }
    }

    private void sendNotification(NewPasswordFormDto newPasswordFormDto, RestorePasswordRequest request) {
        RestorePasswordNotificationMessageDto notificationMessageDto = new RestorePasswordNotificationMessageDto(
                request.getEmail(),
                newPasswordFormDto.getPassword(),
                LocalDateTime.now()
        );
        String json = JsonMapper.objectToJson(notificationMessageDto);
        rabbitTemplate.convertAndSend(request.getResponseQueueName(), json);
        log.info("Notification of password restoring has been sent to the '{}' queue.", request.getResponseQueueName());
    }


    private void checkIfPasswordsMatch(NewPasswordFormDto newPasswordFormDto) {
        if (!newPasswordFormDto.getPassword().equals(newPasswordFormDto.getRepeatPassword())) {
            log.warn("Passwords doesn't match!");
            throw new PasswordsNotMatchRestorePasswordException("Passwords doesn't match!");
        }
    }

    @Override
    @Transactional
    @Scheduled(cron = "0 0/5 * * * ?")
    public void sendPasswordRestoreNotificationMail() {
        List<RestorePasswordRequest> requests = requestRepository
                .findAllBySendingStatus(
                        RestorePasswordRequestStatuses.NOT_SEND,
                        PageRequest.of(0, RESTORE_PASSWORD_MAILS_COUNT)
                );
        log.info("There is found '{}' restore password requests.", requests.size());
        requests.forEach(r -> {
            if (!confirmationCodeRepository.existsByEmail(r.getEmail())) {
                log.warn("Unable to find confirmation code for request '{}'. Request will be removed.", r.getId());
                requestRepository.delete(r);
                return;
            }
            RestorePasswordConfirmationCode code = confirmationCodeRepository.getByEmail(r.getEmail());
            sendEmail(r, code);
            r.setSendingStatus(RestorePasswordRequestStatuses.SENT);
            log.info("Notification mail was sent for the request '{}'.", r.getId());
        });

    }

    private void sendEmail(RestorePasswordRequest request, RestorePasswordConfirmationCode code) {
        String link = restoreMailLink + code.getCode();
        String text = String.format(restoreMailText, request.getLogin(), link);
        emailSender.sendMail(new String[]{code.getEmail()}, restoreMailTitle, text);
    }
}

package com.senla.email.service.impl;

import com.senla.common.json.JsonMapper;
import com.senla.email.dao.EmailConfirmationCodeRepository;
import com.senla.email.dao.MailingRequestRepository;
import com.senla.email.dto.EmailConfirmedNotificationMessageDto;
import com.senla.email.model.EmailConfirmationCode;
import com.senla.email.model.MailingRequest;
import com.senla.email.service.EmailConfirmNotificationService;
import com.senla.email.service.exception.service.confirm.EmailCodeNotFoundException;
import com.senla.email.service.exception.service.confirm.MailingRequestNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@Transactional(readOnly = true)
public class EmailConfirmNotificationServiceImpl implements EmailConfirmNotificationService {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private MailingRequestRepository mailingRequestRepository;

    @Autowired
    private EmailConfirmationCodeRepository emailConfirmationCodeRepository;

    @Override
    @Transactional
    public void confirmEmailVerification(String code) {
        checkIfCodeExists(code);

        EmailConfirmationCode confirmationCode = emailConfirmationCodeRepository.getByConfirmationCode(code);

        checkIfRequestExists(confirmationCode);

        MailingRequest request = mailingRequestRepository.getByRecipientEmail(confirmationCode.getEmail());

        sendNotification(confirmationCode, request);

        log.info("Request '{}' and code '{}' will be removed.", request.getId(), confirmationCode.getId());
        mailingRequestRepository.delete(request);
        emailConfirmationCodeRepository.delete(confirmationCode);
    }

    private void sendNotification(EmailConfirmationCode confirmationCode, MailingRequest request) {
        EmailConfirmedNotificationMessageDto messageDto = new EmailConfirmedNotificationMessageDto(
                confirmationCode.getEmail(),
                LocalDateTime.now()
        );
        String json = JsonMapper.objectToJson(messageDto);
        rabbitTemplate.convertAndSend(request.getResponseQueueName(), json);
        log.info("Email confirm notification has been sent to '{}' queue.", request.getResponseQueueName());
    }

    private void checkIfRequestExists(EmailConfirmationCode confirmationCode) {
        if (!mailingRequestRepository.existsByRecipientEmail(confirmationCode.getEmail())) {
            log.error("Request for with email '{}' not found!", confirmationCode.getEmail());
            throw new MailingRequestNotFoundException(
                    String.format("Request for with email '%s' not found!", confirmationCode.getEmail())
            );
        }
    }

    private void checkIfCodeExists(String code) {
        if (!emailConfirmationCodeRepository.existsByConfirmationCode(code)) {
            log.error("No found no one email with confirmation code '{}'!", code);
            throw new EmailCodeNotFoundException(
                    String.format("No found no one email with confirmation code '%s'!", code)
            );
        }
    }
}

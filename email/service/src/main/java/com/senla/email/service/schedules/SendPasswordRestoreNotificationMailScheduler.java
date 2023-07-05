package com.senla.email.service.schedules;

import com.senla.common.constants.email.RestorePasswordRequestStatuses;
import com.senla.email.dao.RestorePasswordConfirmationCodeRepository;
import com.senla.email.dao.RestorePasswordRequestRepository;
import com.senla.email.model.RestorePasswordConfirmationCode;
import com.senla.email.model.RestorePasswordRequest;
import com.senla.email.service.mail.EmailSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@Transactional(readOnly = true)
public class SendPasswordRestoreNotificationMailScheduler {

    @Value("${password.restore.mail.link}")
    private String restoreMailLink;
    @Value("${password.restore.mail.title}")
    private String restoreMailTitle;
    @Value("${password.restore.mail.text}")
    private String restoreMailText;
    @Value("${sent.password.restore.mails.count}")
    private Integer RESTORE_PASSWORD_MAILS_COUNT;

    @Autowired
    private EmailSender emailSender;
    @Autowired
    private RestorePasswordRequestRepository requestRepository;
    @Autowired
    private RestorePasswordConfirmationCodeRepository confirmationCodeRepository;

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
            if (checkIfRequestCodeExistsForEmail(r)) return;

            RestorePasswordConfirmationCode code = confirmationCodeRepository.getByEmail(r.getEmail());
            sendEmail(r, code);
            r.setSendingStatus(RestorePasswordRequestStatuses.SENT);
            log.info("Notification mail was sent for the request '{}'.", r.getId());
        });
    }

    private boolean checkIfRequestCodeExistsForEmail(RestorePasswordRequest r) {
        if (confirmationCodeRepository.existsByEmail(r.getEmail())) return true;

        log.warn("Unable to find confirmation code for request '{}'. Request will be removed.", r.getId());
        requestRepository.delete(r);
        return false;
    }

    private void sendEmail(RestorePasswordRequest request, RestorePasswordConfirmationCode code) {
        String link = restoreMailLink + code.getCode();
        String text = String.format(restoreMailText, request.getLogin(), link);
        emailSender.sendMail(new String[]{code.getEmail()}, restoreMailTitle, text);
    }

}

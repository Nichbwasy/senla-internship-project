package com.senla.email.service.impl;

import com.senla.common.generators.StringGenerator;
import com.senla.email.dao.EmailConfirmationCodeRepository;
import com.senla.email.dao.MailingRequestRepository;
import com.senla.email.dto.MailingRequestDto;
import com.senla.email.model.EmailConfirmationCode;
import com.senla.email.model.MailingRequest;
import com.senla.email.service.MailingRequestService;
import com.senla.email.service.mail.EmailSender;
import com.senla.email.service.mapper.MailingRequestMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
public class MailingRequestServiceImpl implements MailingRequestService {

    @Value("${mailing.verification.mail.link}")
    private String verificationLink;
    @Value("${mailing.verification.mail.title}")
    private String verificationTitle;
    @Value("${mailing.verification.mail.text}")
    private String verificationText;
    @Value("${mailing.requests.page.size}")
    private Integer MAILING_REQUEST_PAGE_SIZE;
    @Autowired
    private MailingRequestRepository mailingRequestRepository;

    @Autowired
    private MailingRequestMapper mailingRequestMapper;

    @Autowired
    private EmailConfirmationCodeRepository emailConfirmationCodeRepository;
    @Autowired
    private EmailSender emailSender;

    @Override
    public List<MailingRequestDto> showMailingRequestsPage(Integer page) {
        List<MailingRequest> requests = mailingRequestRepository
                .findAll(PageRequest.of(page - 1, MAILING_REQUEST_PAGE_SIZE))
                .getContent();
        log.info("All '{}' mailing requests has been found at page '{}'.", requests.size(), page);
        return requests
                .stream()
                .map(r -> mailingRequestMapper.mapToDto(r))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MailingRequestDto createMailingRequest(MailingRequestDto mailingRequestDto) {
        MailingRequest request = updateOrCreateRequest(mailingRequestDto);

        EmailConfirmationCode confirmationCode = generateVerificationCodeForEmail(mailingRequestDto);

        String mailVerifyLink = verificationLink + confirmationCode.getConfirmationCode();
        String mailText = String.format(verificationText, confirmationCode.getEmail(), mailVerifyLink);
        emailSender.sendMail(new String[]{confirmationCode.getEmail()}, verificationTitle, mailText);

        return mailingRequestMapper.mapToDto(request);
    }

    private MailingRequest updateOrCreateRequest(MailingRequestDto mailingRequestDto) {
        MailingRequest request = null;
        if (mailingRequestRepository.existsByRecipientEmail(mailingRequestDto.getRecipientEmail())) {
            request = mailingRequestRepository.getByRecipientEmail(mailingRequestDto.getRecipientEmail());
            request.setResponseQueueName(mailingRequestDto.getResponseQueueName());
            log.info("Mailing request for email '{}' has been recreated.", mailingRequestDto.getRecipientEmail());
        } else {
            request = mailingRequestRepository.save(mailingRequestMapper.mapToModel(mailingRequestDto));
            log.info("New mailing request '{}' has been saved.", request);
        }
        return request;
    }

    // TODO: There is a possible to generate already existed verification code. If that will happens, DB throws exception.

    private EmailConfirmationCode generateVerificationCodeForEmail(MailingRequestDto mailingRequestDto) {
        String code = StringGenerator.generateString(64);
        EmailConfirmationCode confirmationCode = null;
        if (emailConfirmationCodeRepository.existsByEmail(mailingRequestDto.getRecipientEmail())) {
            confirmationCode = emailConfirmationCodeRepository.getByEmail(mailingRequestDto.getRecipientEmail());
            log.info("Confirmation code for email '{}' has been regenerated.", mailingRequestDto.getRecipientEmail());
        } else {
            confirmationCode = new EmailConfirmationCode(
                    null,
                    mailingRequestDto.getRecipientEmail(),
                    code
            );
            log.info("Confirmation code for email '{}' has been generated.", mailingRequestDto.getRecipientEmail());
        }
        return emailConfirmationCodeRepository.save(confirmationCode);
    }
}

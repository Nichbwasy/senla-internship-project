package com.senla.email.service;

import com.senla.email.dto.MailingRequestDto;

import java.util.List;

public interface MailingRequestService {

    /**
     * Shows page of mailing requests
     * @param page Page number
     * @return List of mailing requests
     */
    List<MailingRequestDto> showMailingRequestsPage(Integer page);

    /**
     * Creates a new record of mailing request
     * @param mailingRequestDto Request form data
     * @return Created mailing request
     */
    MailingRequestDto createMailingRequest(MailingRequestDto mailingRequestDto);

}

package com.senla.email.service;

import com.senla.email.dto.MailingRequestDto;

import java.util.List;

public interface MailingRequestService {

    List<MailingRequestDto> showMailingRequestsPage(Integer page);

    MailingRequestDto createMailingRequest(MailingRequestDto mailingRequestDto);

}

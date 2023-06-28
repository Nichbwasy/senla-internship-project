package com.senla.email.service.impl;

import com.senla.email.dao.MailingRequestRepository;
import com.senla.email.dto.MailingRequestDto;
import com.senla.email.model.MailingRequest;
import com.senla.email.service.MailingRequestService;
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

    @Value("${mailing.requests.page.size}")
    private Integer MAILING_REQUEST_PAGE_SIZE;
    @Autowired
    private MailingRequestRepository mailingRequestRepository;

    @Autowired
    private MailingRequestMapper mailingRequestMapper;

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
}

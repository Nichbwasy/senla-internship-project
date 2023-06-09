package com.senla.rental.service;

import com.senla.rental.dto.RequestDto;
import com.senla.rental.dto.RequestRejectionDto;
import com.senla.rental.dto.controller.requests.RequestsFilterFormDto;
import com.senla.rental.model.Request;
import com.senla.rental.model.RequestRejection;
import jakarta.validation.Valid;

import java.util.List;

public interface RequestsService {
    RequestDto insert(RequestDto requestDto);
    RequestDto update(RequestDto requestDto);
    RequestDto updateRequestStatus(Long requestId, Long requestStatusId);
    RequestDto select(Long id);
    Long delete(Long id);
    List<RequestDto> selectAll();
    List<RequestDto> selectPage(Integer page, RequestsFilterFormDto requestsFilterFormDto);
    List<RequestDto> selectAllForUser(Long userId);
    RequestDto acceptRequest(Long requestId);
    RequestDto rejectRequest(Long requestId, @Valid RequestRejectionDto rejectionDto);
}

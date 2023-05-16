package com.senla.rental.service;

import com.senla.rental.dto.RequestRejectionDto;

import java.util.List;

public interface RequestRejectionsService {
    RequestRejectionDto insert(RequestRejectionDto requestRejectionDto);
    RequestRejectionDto update(RequestRejectionDto requestRejectionDto);
    RequestRejectionDto select(Long id);
    Long delete(Long id);
    List<RequestRejectionDto> selectAll();
}

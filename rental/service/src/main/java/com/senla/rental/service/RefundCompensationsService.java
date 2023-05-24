package com.senla.rental.service;


import com.senla.rental.dto.RefundCompensationDto;

import java.util.List;

public interface RefundCompensationsService {
    RefundCompensationDto insert(RefundCompensationDto refundCompensationDto);
    RefundCompensationDto update(RefundCompensationDto refundCompensationDto);
    RefundCompensationDto select(Long id);
    Long delete(Long id);
    List<RefundCompensationDto> selectAll();
}

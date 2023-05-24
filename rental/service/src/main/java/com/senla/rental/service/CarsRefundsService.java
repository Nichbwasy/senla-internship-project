package com.senla.rental.service;

import com.senla.rental.dto.CarRefundDto;
import com.senla.rental.dto.controller.CreateCarRefundFormDto;
import jakarta.validation.Valid;

import java.util.List;

public interface CarsRefundsService {
    CarRefundDto insert(CarRefundDto carRefundDto);
    CarRefundDto update(CarRefundDto carRefundDto);
    CarRefundDto select(Long id);
    Long delete(Long id);
    List<CarRefundDto> selectAll();
    List<CarRefundDto> selectRefundsPage(Integer page);
    CarRefundDto createCarRefund(@Valid CreateCarRefundFormDto carRefundDto);
}

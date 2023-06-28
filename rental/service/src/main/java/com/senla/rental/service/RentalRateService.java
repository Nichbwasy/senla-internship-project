package com.senla.rental.service;


import com.senla.rental.dto.RentalRateDto;

import java.util.List;

public interface RentalRateService {

    RentalRateDto insert(RentalRateDto requestDto);
    RentalRateDto update(RentalRateDto requestDto);
    RentalRateDto select(Long id);
    Long delete(Long id);
    List<RentalRateDto> selectAll();

}

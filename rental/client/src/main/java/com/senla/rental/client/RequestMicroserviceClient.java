package com.senla.rental.client;

import com.senla.rental.dto.RequestDto;

import java.util.List;

public interface RequestMicroserviceClient {

    List<RequestDto> getAllUserRequests(Long userId);
    RequestDto updateRequest(RequestDto requestDto);


}

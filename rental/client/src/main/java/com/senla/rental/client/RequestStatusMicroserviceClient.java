package com.senla.rental.client;

import com.senla.rental.dto.RequestStatusDto;

public interface RequestStatusMicroserviceClient {

    RequestStatusDto getRequestStatusByName(String name);

}

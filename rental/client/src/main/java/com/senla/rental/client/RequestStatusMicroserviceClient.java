package com.senla.rental.client;

import com.senla.rental.dto.RequestStatusDto;

public interface RequestStatusMicroserviceClient {

    /**
     * Returns request status by name
     * @param name Name of request status
     * @return Found request status record
     */
    RequestStatusDto getRequestStatusByName(String name);

}

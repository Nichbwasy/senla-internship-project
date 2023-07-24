package com.senla.rental.client;

import com.senla.rental.dto.RequestDto;

import java.util.List;

public interface RequestMicroserviceClient {

    /**
     * Returns all user's car ordering requests
     * @param userId Id of user
     * @return List of all car ordering request for the user
     */
    List<RequestDto> getAllUserRequests(Long userId);

    /**
     * Updated user's car ordering request status
     * @param requestId Id of car ordering request
     * @param requestStatusId Id of status to change
     * @return Updated car ordering request
     */
    RequestDto updateRequestStatus(Long requestId, Long requestStatusId);


}

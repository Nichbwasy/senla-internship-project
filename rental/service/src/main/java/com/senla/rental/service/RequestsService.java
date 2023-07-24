package com.senla.rental.service;

import com.senla.payment.dto.PaymentReceiptDto;
import com.senla.rental.dto.RequestDto;
import com.senla.rental.dto.RequestRejectionDto;
import com.senla.rental.dto.controller.requests.RequestsFilterFormDto;
import com.senla.rental.model.Request;
import com.senla.rental.model.RequestRejection;
import jakarta.validation.Valid;

import java.util.List;

public interface RequestsService {

    /**
     * Inserts a new request record
     * @param requestDto Request dto to insert
     * @return Inserted request record
     */
    RequestDto insert(RequestDto requestDto);

    /**
     * Updates request record 
     * @param requestDto Request to update
     * @return Updated request record
     */
    RequestDto update(RequestDto requestDto);

    /**
     * Sets car ordering request status as 'PAYED'
     * @param paymentReceiptDto Receipt from payment system
     * @return Updated car ordering request record
     */
    RequestDto setRequestStatusToPayed(PaymentReceiptDto paymentReceiptDto);

    /**
     * Selects request record by id 
     * @param id Request id
     * @return Selected by id record
     */
    RequestDto select(Long id);

    /**
     * Deletes request by id
     * @param id Request id
     * @return Deleted request id
     */
    Long delete(Long id);

    /**
     * Selects all request records
     * @return List of all request records
     */
    List<RequestDto> selectAll();

    /**
     * Shows page of car ordering requests
     * @param page Page number
     * @param requestsFilterFormDto Filtering form
     * @return List of filtered car ordering requests for the specified page
     */
    List<RequestDto> selectPage(Integer page, RequestsFilterFormDto requestsFilterFormDto);

    /**
     * Select car ordering requests for the user with specified id
     * @param userId User's id
     * @return List of all car ordering requests for the user
     */
    List<RequestDto> selectAllForUser(Long userId);

    /**
     * Accepts car ordering request
     * @param requestId Request's id
     * @return Updated car ordering request record
     */
    RequestDto acceptRequest(Long requestId);

    /**
     * Rejects car ordering request
     * @param requestId Request's id
     * @param rejectionDto Request rejection for with causes of rejection
     * @return Updated car ordering request record
     */
    RequestDto rejectRequest(Long requestId, @Valid RequestRejectionDto rejectionDto);
}

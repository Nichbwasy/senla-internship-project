package com.senla.rental.service;

import com.senla.rental.dto.CarRefundDto;
import com.senla.rental.dto.RefundCompensationDto;
import com.senla.rental.dto.RequestDto;
import com.senla.rental.dto.controller.UserProfileDto;

import java.util.List;

public interface UserProfileService {

    /**
     * Returns user profile
     * @param userId User's id
     * @return User's profile
     */
    UserProfileDto getUserProfile(Long userId);

    /**
     * Show details of user's car ordering request
     * @param userId User's id
     * @param requestId Request's id
     * @return User's car ordering request
     */
    RequestDto showUserRequest(Long userId, Long requestId);

    /**
     * Cancels user's car ordering request
     * @param userId User's id
     * @param requestId Request's id
     * @return Id of canceled car ordering request
     */
    Long cancelUserRequest(Long userId, Long requestId);

    /**
     * Gets page of users car refunds
     * @param page Page number
     * @param userId User's id
     * @return List of found car refunds for specified page
     */
    List<CarRefundDto> getAllUserRefunds(Integer page, Long userId);

    /**
     * Shows car refund details
     * @param userId User's id
     * @param refundId Refund's id
     * @return Car refund details
     */
    CarRefundDto showUserRefundDetails(Long userId, Long refundId);

    /**
     * Pays compensations for car refund request
     * @param userId User's id
     * @param refundId Refund's id
     * @return Created refund compensations record
     */
    RefundCompensationDto payCompensation(Long userId, Long refundId);

}

package com.senla.rental.service;

import com.senla.rental.dto.CarRefundDto;
import com.senla.rental.dto.RefundCompensationDto;
import com.senla.rental.dto.RequestDto;
import com.senla.rental.dto.controller.UserProfileDto;

import java.util.List;

public interface UserProfileService {

    UserProfileDto getUserProfile(Long userId);
    RequestDto showUserRequest(Long userId, Long requestId);
    Long cancelUserRequest(Long userId, Long requestId);
    List<CarRefundDto> getAllUserRefunds(Integer page, Long userId);
    CarRefundDto showUserRefundDetails(Long userId, Long refundId);
    RefundCompensationDto payCompensation(Long userId, Long refundId);

}

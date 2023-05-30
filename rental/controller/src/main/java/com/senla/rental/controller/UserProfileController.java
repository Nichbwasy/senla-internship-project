package com.senla.rental.controller;

import com.senla.common.annotations.LogMethodExecution;
import com.senla.common.security.utils.JwtTokenUtils;
import com.senla.rental.dto.CarRefundDto;
import com.senla.rental.dto.RefundCompensationDto;
import com.senla.rental.dto.RequestDto;
import com.senla.rental.dto.controller.UserProfileDto;
import com.senla.rental.service.UserProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@LogMethodExecution
@RequestMapping("/profile")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    @GetMapping
    public ResponseEntity<UserProfileDto> showUserProfile(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        log.info("Trying to show user profile...");
        Long userId = JwtTokenUtils.getAccessTokenUserId(authorization.substring(7));
        return ResponseEntity.ok().body(userProfileService.getUserProfile(userId));
    }

    @GetMapping("/request/{id}")
    public ResponseEntity<RequestDto> showUserRequest(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
                                                      @PathVariable Long id) {
        log.info("Trying to show a user request...");
        Long userId = JwtTokenUtils.getAccessTokenUserId(authorization.substring(7));
        return ResponseEntity.ok().body(userProfileService.showUserRequest(userId, id));
    }

    @GetMapping("/refunds/{page}")
    public ResponseEntity<List<CarRefundDto>> showUserRefunds(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
                                                              @PathVariable Integer page) {
        log.info("Trying to show a user all refunds...");
        Long userId = JwtTokenUtils.getAccessTokenUserId(authorization.substring(7));
        return ResponseEntity.ok().body(userProfileService.getAllUserRefunds(page, userId));
    }

    @GetMapping("/refunds/refund/{refundId}")
    public ResponseEntity<CarRefundDto> showUserRefundDetails(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
                                                              @PathVariable Long refundId) {
        log.info("Trying to show a user refund details ...");
        Long userId = JwtTokenUtils.getAccessTokenUserId(authorization.substring(7));
        return ResponseEntity.ok().body(userProfileService.showUserRefundDetails(userId, refundId));
    }

    @PostMapping("/refunds/refund/{refundId}")
    public ResponseEntity<RefundCompensationDto> payCompensation(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
                                                                 @PathVariable Long refundId) {
        log.info("Trying to pay compensation...");
        Long userId = JwtTokenUtils.getAccessTokenUserId(authorization.substring(7));
        return ResponseEntity.ok().body(userProfileService.payCompensation(userId, refundId));
    }


    @DeleteMapping("/request/{id}")
    public ResponseEntity<Long> cancelRequest(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
                                              @PathVariable Long id) {
        log.info("Trying to cancel request...");
        Long userId = JwtTokenUtils.getAccessTokenUserId(authorization.substring(7));
        return ResponseEntity.ok().body(userProfileService.cancelUserRequest(userId, id));
    }

}

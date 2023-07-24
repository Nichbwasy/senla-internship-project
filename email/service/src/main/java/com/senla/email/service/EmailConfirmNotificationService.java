package com.senla.email.service;

public interface EmailConfirmNotificationService {

    /**
     * Confirms email verification and sends notification to the authorization microservice
     * @param code Code of verification request
     */
    void confirmEmailVerification(String code);

}

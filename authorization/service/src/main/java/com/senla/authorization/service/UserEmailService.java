package com.senla.authorization.service;

import com.senla.authorization.dto.UserDataDto;
import com.senla.email.dto.EmailConfirmedNotificationMessageDto;

public interface UserEmailService {

    UserDataDto confirmUserEmail(EmailConfirmedNotificationMessageDto messageDto);

}

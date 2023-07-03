package com.senla.email.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestorePasswordNotificationMessageDto {

    private String email;
    private String newPassword;
    private LocalDateTime time;

}

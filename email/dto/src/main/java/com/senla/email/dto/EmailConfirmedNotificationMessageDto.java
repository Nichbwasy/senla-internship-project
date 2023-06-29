package com.senla.email.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailConfirmedNotificationMessageDto {

    private String email;
    private LocalDateTime time;

}

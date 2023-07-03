package com.senla.email.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestorePasswordRequestDto {

    private Long id;
    private String login;
    private String email;
    private LocalDateTime time;
    private String responseQueueName;


}

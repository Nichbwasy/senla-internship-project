package com.senla.email.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailingRequestDto {

    private Long id;
    private String recipientEmail;
    private String responseQueueName;

}

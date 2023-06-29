package com.senla.email.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmationMailDto {

    private Long id;
    private String title;
    private String text;
    private String recipientEmail;

}

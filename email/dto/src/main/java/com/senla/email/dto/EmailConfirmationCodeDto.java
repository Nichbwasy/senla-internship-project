package com.senla.email.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailConfirmationCodeDto {

    private Long id;
    private String email;
    private String confirmationCode;

}

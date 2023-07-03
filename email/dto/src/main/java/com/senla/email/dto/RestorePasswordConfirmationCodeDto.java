package com.senla.email.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestorePasswordConfirmationCodeDto {

    private Long id;
    private String email;
    private String code;

}

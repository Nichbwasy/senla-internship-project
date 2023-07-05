package com.senla.email.dto.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewPasswordFormDto {

    private String password;
    private String repeatPassword;

}

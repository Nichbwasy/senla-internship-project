package com.senla.authorization.dto.controllers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePasswordRestoreRequestDto {

    private String login;

}

package com.senla.rental.dto.controller;

import com.senla.authorization.dto.UserDataDto;
import com.senla.rental.dto.RequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDto {

    private UserDataDto userData;
    private List<RequestDto> requests;

}

package com.senla.authorization.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDataDto implements Serializable {
    private Long id;
    private String login;
    private String email;
    private String emailStatus;
    private List<RoleDto> roles;

}

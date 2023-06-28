package com.senla.authorization.dto.registrations;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDataDto {

    @NotNull(message = "UserData login is mandatory!")
    @Size(min = 3, max = 128, message = "UserData login must contains from 3 to 128 characters!")
    private String login;

    @NotNull(message = "User password is mandatory!")
    @Size(min = 3, max = 64, message = "UserData password must contains from 3 to 64 characters!")
    @Pattern(regexp = "^[a-zA-Z0-9._@#$%^&*!=+]{3,64}$", message = "Password has a incorrect format!")
    private String password;

    @NotNull(message = "User email is mandatory!")
    @Size(min = 3, max = 128, message = "Email must contains from 3 to 128 characters!")
    @Pattern(regexp = "[a-zA-Z0-9\\.\\-\\_]+[@][a-zA-Z0-9]+[.][a-zA-Z]{2,9}", message = "Email has a wrong format!")
    private String email;

    @NotNull(message = "User repeat password is mandatory!")
    @Size(min = 3, max = 64, message = "UserData password must contains from 3 to 64 characters!")
    @Pattern(regexp = "^[a-zA-Z0-9._@#$%^&*!=+]{3,64}$", message = "Password has a incorrect format!")
    private String repeatPassword;

}

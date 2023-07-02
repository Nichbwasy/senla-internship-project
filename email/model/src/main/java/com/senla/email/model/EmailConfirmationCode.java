package com.senla.email.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "emailconfirmationcode")
public class EmailConfirmationCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Email is mandatory!")
    @Size(min = 3, max = 128, message = "Email must contains from 3 to 128 characters!")
    @Column(name = "email", length = 128, unique = true, nullable = false)
    private String email;

    @NotNull(message = "Confirmation code is mandatory!")
    @Size(min = 64, max = 64, message = "Confirmation code must contains 64 characters!")
    @Column(name = "confirmation_code", length = 64, unique = true, nullable = false)
    private String confirmationCode;

}

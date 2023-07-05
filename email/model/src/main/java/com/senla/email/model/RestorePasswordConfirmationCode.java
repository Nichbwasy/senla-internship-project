package com.senla.email.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "restorepasswordconfirmationcode")
public class RestorePasswordConfirmationCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Email is mandatory!")
    @Size(min = 3, max = 128, message = "Email's length must be from 3 to 128 characters!")
    @Column(name = "email", length = 128, unique = true, nullable = false)
    private String email;

    @NotNull(message = "Code is mandatory!")
    @Size(min = 64, max = 64, message = "Code must contains 64 characters!")
    @Column(name = "code", length = 64, nullable = false, unique = true)
    private String code;

    @NotNull(message = "Password restore request is mandatory!")
    @OneToOne(targetEntity = RestorePasswordRequest.class, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private RestorePasswordRequest request;

}

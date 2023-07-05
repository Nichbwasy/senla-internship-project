package com.senla.email.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestorePasswordConfirmationCodeDto {

    private Long id;
    private String email;
    private String code;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private RestorePasswordRequestDto request;

}

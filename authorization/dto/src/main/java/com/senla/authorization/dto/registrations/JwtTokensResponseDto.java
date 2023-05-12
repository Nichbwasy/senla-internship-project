package com.senla.authorization.dto.registrations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtTokensResponseDto {

    private String accessToken;
    private String refreshToken;

}

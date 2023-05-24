
package com.senla.common.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccessRefreshTokensDto {

    private String accessToken;
    private String refreshToken;

}

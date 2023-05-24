package com.senla.authorization.service;

import com.senla.authorization.dao.UserDataRepository;
import com.senla.authorization.dao.UserRefreshTokenRepository;
import com.senla.authorization.service.impl.TokenServiceImpl;
import com.senla.authorization.service.jwt.providers.JwtTokenProvider;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class TokenServiceTests {

    @InjectMocks
    private TokenService tokenService = new TokenServiceImpl();
    @Mock
    private UserDataRepository userDataRepository;
    @Mock
    private UserRefreshTokenRepository refreshTokenRepository;
    @Mock
    private JwtTokenProvider jwtTokenProvider;


}

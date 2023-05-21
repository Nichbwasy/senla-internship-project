package com.senla.authorization.service;

import com.senla.authorization.dao.UserDataRepository;
import com.senla.authorization.dao.UserRefreshTokenRepository;
import com.senla.authorization.dto.registrations.JwtTokensResponseDto;
import com.senla.authorization.dto.registrations.LogInUserDto;
import com.senla.authorization.model.UserData;
import com.senla.authorization.model.UserRefreshToken;
import com.senla.authorization.service.encoders.PasswordEncoder;
import com.senla.authorization.service.exceptions.jwt.AccessTokenGenerationException;
import com.senla.authorization.service.exceptions.services.authorization.UserNotMatchPasswordException;
import com.senla.authorization.service.exceptions.services.authorization.UserNotRegisteredException;
import com.senla.authorization.service.impl.AuthorizationServiceImpl;
import com.senla.authorization.service.jwt.providers.JwtTokenProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class AuthorizationServiceTests {
    @InjectMocks
    private AuthorizationService authorizationService = new AuthorizationServiceImpl();

    @Mock
    private UserDataRepository userDataRepository;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private UserRefreshTokenRepository refreshTokenRepository;

    @Test
    public void logInUserTest() {
        String password = PasswordEncoder.encodeString("Password");
        UserData userData = new UserData(1L, "Login", password, null);
        UserRefreshToken userRefreshToken = new UserRefreshToken(1L, userData, "OldRefreshToken");

        Mockito.when(userDataRepository.existsByLogin("Login")).thenReturn(true);
        Mockito.when(userDataRepository.findByLogin("Login")).thenReturn(userData);
        Mockito.when(jwtTokenProvider.generateAccessToken(Mockito.any(UserData.class))).thenReturn("AccessToken");
        Mockito.when(jwtTokenProvider.generateRefreshToken(Mockito.any(UserData.class))).thenReturn("RefreshToken");
        Mockito.when(refreshTokenRepository.existsByUserData(Mockito.any(UserData.class))).thenReturn(true);
        Mockito.when(refreshTokenRepository.findByUserData(Mockito.any(UserData.class))).thenReturn(userRefreshToken);

        JwtTokensResponseDto tokens = authorizationService.logInUser(new LogInUserDto("Login", "Password"));
        Assertions.assertEquals("AccessToken", tokens.getAccessToken());
        Assertions.assertEquals("RefreshToken", tokens.getRefreshToken());
        Assertions.assertEquals("RefreshToken", userRefreshToken.getRefreshToken());
    }

    @Test
    public void logInUserRefreshTokenNotExistInDBTest() {
        String password = PasswordEncoder.encodeString("Password");
        UserData userData = new UserData(1L, "Login", password, null);

        Mockito.when(userDataRepository.existsByLogin("Login")).thenReturn(true);
        Mockito.when(userDataRepository.findByLogin("Login")).thenReturn(userData);
        Mockito.when(jwtTokenProvider.generateAccessToken(Mockito.any(UserData.class))).thenReturn("AccessToken");
        Mockito.when(jwtTokenProvider.generateRefreshToken(Mockito.any(UserData.class))).thenReturn("RefreshToken");
        Mockito.when(refreshTokenRepository.existsByUserData(Mockito.any(UserData.class))).thenReturn(false);
        Mockito.when(refreshTokenRepository.save(Mockito.any(UserRefreshToken.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());

        JwtTokensResponseDto tokens = authorizationService.logInUser(new LogInUserDto("Login", "Password"));
        Assertions.assertEquals("AccessToken", tokens.getAccessToken());
        Assertions.assertEquals("RefreshToken", tokens.getRefreshToken());
    }

    @Test
    public void logInUserPasswordsNotMatchTest() {
        String password = PasswordEncoder.encodeString("WrongPassword");
        UserData userData = new UserData(1L, "Login", password, null);

        Mockito.when(userDataRepository.existsByLogin("Login")).thenReturn(true);
        Mockito.when(userDataRepository.findByLogin("Login")).thenReturn(userData);
        Mockito.when(jwtTokenProvider.generateAccessToken(Mockito.any(UserData.class))).thenReturn("AccessToken");
        Mockito.when(jwtTokenProvider.generateRefreshToken(Mockito.any(UserData.class))).thenReturn("RefreshToken");

        Assertions.assertThrows(UserNotMatchPasswordException.class, () ->
                authorizationService.logInUser(new LogInUserDto("Login", "Password")));
    }

    @Test
    public void logInUserPasswordsUserNotExistsTest() {
        Mockito.when(userDataRepository.existsByLogin("Login")).thenReturn(false);

        Assertions.assertThrows(UserNotRegisteredException.class, () ->
                authorizationService.logInUser(new LogInUserDto("Login", "Password")));
    }

    @Test
    public void logInUserPasswordsTokenProviderExceptionTest() {
        String password = PasswordEncoder.encodeString("Password");
        UserData userData = new UserData(1L, "Login", password, null);

        Mockito.when(userDataRepository.existsByLogin("Login")).thenReturn(true);
        Mockito.when(userDataRepository.findByLogin("Login")).thenReturn(userData);
        Mockito.when(jwtTokenProvider.generateAccessToken(Mockito.any(UserData.class)))
                .thenThrow(AccessTokenGenerationException.class);

        Assertions.assertThrows(AccessTokenGenerationException.class, () ->
                authorizationService.logInUser(new LogInUserDto("Login", "Password")));
    }

    @Test
    public void loginWithNullDataTest() {
        Mockito.when(userDataRepository.existsByLogin(Mockito.nullable(String.class)))
                .thenThrow(NullPointerException.class);

        Assertions.assertThrows(NullPointerException.class, () ->
                authorizationService.logInUser(new LogInUserDto(null, null)));
    }

    @Test
    public void loginWithNullPasswordDataTest() {
        String password = PasswordEncoder.encodeString("Password");
        UserData userData = new UserData(1L, "Login", password, null);

        Mockito.when(userDataRepository.existsByLogin("Login")).thenReturn(true);
        Mockito.when(userDataRepository.findByLogin("Login")).thenReturn(userData);

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                authorizationService.logInUser(new LogInUserDto("Login", null)));
    }

}

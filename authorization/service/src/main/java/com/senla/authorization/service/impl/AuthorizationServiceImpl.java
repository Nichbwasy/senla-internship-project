package com.senla.authorization.service.impl;

import com.senla.authorization.dao.UserDataRepository;
import com.senla.authorization.dao.UserRefreshTokenRepository;
import com.senla.authorization.dto.registrations.JwtTokensResponseDto;
import com.senla.authorization.dto.registrations.LogInUserDto;
import com.senla.authorization.model.UserData;
import com.senla.authorization.model.UserRefreshToken;
import com.senla.authorization.service.AuthorizationService;
import com.senla.authorization.service.encoders.PasswordEncoder;
import com.senla.authorization.service.exceptions.services.authorization.UserNotMatchPasswordException;
import com.senla.authorization.service.exceptions.services.authorization.UserNotRegisteredException;
import com.senla.authorization.service.jwt.providers.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class AuthorizationServiceImpl implements AuthorizationService {

    @Autowired
    private UserDataRepository userDataRepository;

    @Autowired
    private UserRefreshTokenRepository refreshTokenRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public JwtTokensResponseDto logInUser(LogInUserDto dto) {
        UserData user = checkAuthorizationForm(dto);

        String accessToken = jwtTokenProvider.generateAccessToken(user);
        String refreshToken = jwtTokenProvider.generateRefreshToken(user);
        log.info("Access/refresh tokens has been generated for the user '{}'.", user.getId());

        saveRefreshTokenInDB(user, refreshToken);

        return new JwtTokensResponseDto(accessToken, refreshToken);
    }

    private UserData checkAuthorizationForm(LogInUserDto dto) {
        log.info("Trying authorize user '{}'...", dto.getLogin());
        if (!userDataRepository.existsByLogin(dto.getLogin())) {
            log.warn("Unable authorize user '{}'! User doesn't registered!", dto.getLogin());
            throw new UserNotRegisteredException(
                    String.format("Unable authorize user '%s'! User doesn't registered!", dto.getLogin())
            );
        }

        UserData user = userDataRepository.findByLogin(dto.getLogin());
        if (!PasswordEncoder.match(dto.getPassword(), user.getPassword())) {
            log.warn("Unable authorize user '{}'! Password doesn't match!", dto.getLogin());
            throw new UserNotMatchPasswordException(
                    String.format("Unable authorize user '%s'! Password doesn't match!", dto.getLogin())
            );
        }
        return user;
    }

    private void saveRefreshTokenInDB(UserData user, String refreshToken) {
        if (refreshTokenRepository.existsByUserData(user)) {
            UserRefreshToken userRefreshToken = refreshTokenRepository.findByUserData(user);
            userRefreshToken.setRefreshToken(refreshToken);
            log.info("Refresh token for the user '{}' has been updated.", user.getId() );
        } else {
            refreshTokenRepository.save(new UserRefreshToken(user, refreshToken));
            log.info("New record of refresh token has been created to the user '{}'.", user.getId());
        }
    }
}

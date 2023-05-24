package com.senla.authorization.service.impl;

import com.senla.authorization.dao.UserDataRepository;
import com.senla.authorization.dao.UserRefreshTokenRepository;
import com.senla.authorization.model.UserData;
import com.senla.authorization.model.UserRefreshToken;
import com.senla.authorization.service.TokenService;
import com.senla.authorization.service.exceptions.jwt.JwtTokenMatchException;
import com.senla.authorization.service.exceptions.jwt.JwtTokenNoRefreshTokenInDatabaseException;
import com.senla.authorization.service.jwt.providers.JwtTokenProvider;
import com.senla.authorization.service.exceptions.jwt.JwtTokenUserNotFoundException;
import com.senla.common.exception.security.jwt.JwtTokenValidationException;
import com.senla.common.security.dto.AccessRefreshTokensDto;
import com.senla.common.security.utils.JwtTokenUtils;
import com.senla.common.reflections.validators.JwtTokenValidator;
import com.senla.common.reflections.validators.TokenStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class TokenServiceImpl implements TokenService {

    @Autowired
    private UserDataRepository userDataRepository;
    @Autowired
    private UserRefreshTokenRepository refreshTokenRepository;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public AccessRefreshTokensDto refreshTokens(AccessRefreshTokensDto tokens) {
        log.info("Trying to refresh access/refresh tokens...");

        checkTokenStatus(tokens);

        String userLogin = checkUserExistence(tokens);

        UserData user = checkSavedRefreshToken(userLogin);

        UserRefreshToken savedToken = checkTokensMatch(tokens, user);

        String newAccessToken = jwtTokenProvider.generateAccessToken(user);
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(user);
        log.info("New access/refresh tokens has been created.");
        savedToken.setRefreshToken(newRefreshToken);
        log.info("Refresh token for the user '{}' has been updated.", user.getId());
        return new AccessRefreshTokensDto(newAccessToken, newRefreshToken);
    }

    private UserRefreshToken checkTokensMatch(AccessRefreshTokensDto tokens, UserData user) {
        UserRefreshToken savedToken = refreshTokenRepository.findByUserData(user);
        if (!savedToken.getRefreshToken().equals(tokens.getRefreshToken())) {
            log.warn("Can't refresh tokens! Refresh token doesn't match with last created one!");
            throw new JwtTokenMatchException("Can't refresh tokens! Refresh token doesn't match with last created one!");
        }
        return savedToken;
    }

    private UserData checkSavedRefreshToken(String userLogin) {
        UserData user = userDataRepository.findByLogin(userLogin);
        if (!refreshTokenRepository.existsByUserData(user)) {
            log.warn("Can't refresh tokens! Refresh token in database fo the user '{}' doesn't match with refreshed token in request!",
                    user.getLogin());
            throw new JwtTokenNoRefreshTokenInDatabaseException(
                    String.format("Can't refresh tokens! Refresh token in database fo the user '%s'" +
                                    " doesn't match with refreshed token in request!",
                            user.getLogin())
            );
        }
        return user;
    }

    private String checkUserExistence(AccessRefreshTokensDto tokens) {
        String userLogin = JwtTokenUtils.getRefreshTokenUserLogin(tokens.getRefreshToken());
        if (!userDataRepository.existsByLogin(userLogin)) {
            log.warn("Can't refresh tokens! User login '{}' in refresh token not found in database!", userLogin);
            throw new JwtTokenUserNotFoundException(
                    String.format("Unable refresh token, because user with login '%s' not found in database!", userLogin)
            );
        }
        return userLogin;
    }

    private void checkTokenStatus(AccessRefreshTokensDto tokens) {
        TokenStatus tokenStatus = JwtTokenValidator.validateRefreshToken(tokens.getRefreshToken());
        if (!tokenStatus.equals(TokenStatus.OK)) {
            log.warn("Can't refresh tokens! Refresh token not valid!");
            throw new JwtTokenValidationException("Can't refresh tokens! Refresh token not valid!");
        }
    }
}

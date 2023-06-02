package com.senla.common.security.filters;

import com.senla.common.clients.RefreshTokensMicroserviceClient;
import com.senla.common.exception.security.jwt.JwtTokenNotFoundException;
import com.senla.common.exception.security.jwt.JwtTokenValidationException;
import com.senla.common.exception.security.jwt.statuses.JwtTokenMalformedException;
import com.senla.common.exception.security.jwt.statuses.JwtTokenSignatureException;
import com.senla.common.exception.security.jwt.statuses.JwtTokenUnsupportedException;
import com.senla.starter.jwt.security.utils.authentication.JwtAuthenticationUtils;
import com.senla.starter.jwt.security.utils.consts.TokenStatus;
import com.senla.starter.jwt.security.utils.dto.AccessRefreshTokensDto;
import com.senla.starter.jwt.security.utils.dto.JwtAuthenticationDto;
import com.senla.starter.jwt.security.utils.utils.JwtTokenUtils;
import com.senla.starter.jwt.security.utils.validators.JwtTokenValidator;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.List;

@Slf4j
public class JwtTokenSecurityCommonFilter extends GenericFilterBean {

    //TODO: Fix null @Autowired fields
    @Autowired
    private JwtTokenUtils jwtTokenUtils;
    @Autowired
    private JwtTokenValidator jwtTokenValidator;
    @Autowired
    private JwtAuthenticationUtils jwtAuthenticationUtils;
    @Autowired
    private RefreshTokensMicroserviceClient refreshTokensMicroserviceClient;
    private List<String> ignoredPaths;

    private final static String AUTHORIZATION_HEADER = "Authorization";
    private final static String REFRESH_HEADER = "Refresh";
    private final static String BEARER = "Bearer ";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String path = ((HttpServletRequest) servletRequest).getServletPath();

        if (checkIgnores(path)) {
            log.info("Request for the path '{}' ignores filter.", path);
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String accessToken = getAccessTokenFromRequest((HttpServletRequest) servletRequest);
        TokenStatus tokenStatus = jwtTokenValidator.validateAccessToken(accessToken);

        switch (tokenStatus) {
            case OK -> {
                log.info("Access token is valid.");
                UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            case EXPIRED -> {
                log.info("Access token not valid! Trying to refresh tokens... {}", accessToken);
                String refreshToken = getRefreshTokenFromRequest((HttpServletRequest) servletRequest);
                refreshTokens((HttpServletResponse) servletResponse, new AccessRefreshTokensDto(accessToken, refreshToken));
            }
            default -> resolveTokenStatus(tokenStatus);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void refreshTokens(HttpServletResponse servletResponse, AccessRefreshTokensDto accessRefreshTokens) {
        AccessRefreshTokensDto refreshedTokens =
                refreshTokensMicroserviceClient.refreshTokens(accessRefreshTokens);
        servletResponse.addHeader(AUTHORIZATION_HEADER, refreshedTokens.getAccessToken());
        servletResponse.addHeader(REFRESH_HEADER, refreshedTokens.getRefreshToken());

        UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(refreshedTokens.getAccessToken());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(String token) {
        Claims claims = jwtTokenUtils.getAccessTokenClaims(token);
        JwtAuthenticationDto jwtInfoToken = jwtAuthenticationUtils.generateJwtAuthentication(claims);
        return new UsernamePasswordAuthenticationToken(
                jwtInfoToken.getUsername(),
                null,
                jwtInfoToken.getAuthorities());
    }

    private String getAccessTokenFromRequest(HttpServletRequest httpRequest) {
        String token = httpRequest.getHeader(AUTHORIZATION_HEADER);
        if (token != null) {
            if (token.startsWith(BEARER)) {
                return token.substring(7);
            } else {
                log.warn("Invalid token! Access token must starts with 'Bearer'!");
                throw new JwtTokenValidationException("Invalid token! Access token must starts with 'Bearer'!");
            }
        } else {
            log.warn("Access token not found in request!");
            throw new JwtTokenNotFoundException("Access token not found in request!");
        }
    }

    private String getRefreshTokenFromRequest(HttpServletRequest httpRequest) {
        String token = httpRequest.getHeader(REFRESH_HEADER);
        if (token != null) {
            return token;
        } else {
            log.warn("Refresh token not found in request!");
            throw new JwtTokenNotFoundException("Refresh token not found in request!");
        }
    }

    private void resolveTokenStatus(TokenStatus tokenStatus) {
        log.warn("Access token not valid! Resolving token status...");
        switch (tokenStatus) {
            case UNSUPPORTED -> throw new JwtTokenUnsupportedException("Token validation exception! Token unsupported!");
            case WRONG_SIGNATURE -> throw new JwtTokenSignatureException("Token validation exception! Token has wrong signature!");
            case MALFORMED -> throw new JwtTokenMalformedException("Token validation exception! Token is malformed!");
            case INVALID -> throw new JwtTokenValidationException("Token validation exception! Token invalid!");
        }
    }

    private Boolean checkIgnores(String url) {
        return ignoredPaths.stream().anyMatch(
                path -> {
                    if (path.contains("/**")) return url.startsWith(path.replace("/**", ""));
                    else return url.equals(path);
                }
        );
    }
    
    public void setIgnoredPaths(List<String> paths) {
        ignoredPaths = paths;
    }

}

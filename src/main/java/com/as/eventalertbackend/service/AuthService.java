package com.as.eventalertbackend.service;

import com.as.eventalertbackend.AppProperties;
import com.as.eventalertbackend.dto.request.AuthLoginRequest;
import com.as.eventalertbackend.dto.request.AuthRegisterRequest;
import com.as.eventalertbackend.dto.response.AuthTokensResponse;
import com.as.eventalertbackend.enums.Role;
import com.as.eventalertbackend.error.ApiErrorMessage;
import com.as.eventalertbackend.error.exception.InvalidActionException;
import com.as.eventalertbackend.persistence.entity.User;
import com.as.eventalertbackend.persistence.entity.UserRole;
import com.as.eventalertbackend.security.jwt.JwtManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

@Service
@Transactional
@Slf4j
public class AuthService {

    private final AppProperties appProperties;

    private final UserService userService;
    private final UserRoleService userRoleService;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtManager jwtManager;

    @Autowired
    public AuthService(AppProperties appProperties,
                       UserService userService,
                       UserRoleService userRoleService,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtManager jwtManager) {
        this.appProperties = appProperties;
        this.userService = userService;
        this.userRoleService = userRoleService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtManager = jwtManager;
    }

    public User register(AuthRegisterRequest registerRequest) {
        boolean emailExists = userService.existsByEmail(registerRequest.getEmail());
        if (emailExists) {
            throw new InvalidActionException(ApiErrorMessage.ACCOUNT_ALREADY_CREATED);
        }

        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            throw new InvalidActionException(ApiErrorMessage.PASSWORDS_NOT_MATCH);
        }

        UserRole userRole = userRoleService.findByName(Role.ROLE_USER);

        User user = new User(registerRequest.getEmail(),
                passwordEncoder.encode(registerRequest.getPassword()),
                Collections.singleton(userRole));

        return userService.save(user);
    }

    public AuthTokensResponse login(AuthLoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        if (!appProperties.getMockedUsersEmails().contains(email)) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        String accessTokenInfo = jwtManager.generateAccessToken(email);
        String refreshTokenInfo = jwtManager.generateRefreshToken(email);

        log.info("Login tokens generated for {}", email);
        return new AuthTokensResponse(accessTokenInfo, refreshTokenInfo);
    }

    public AuthTokensResponse refreshToken(HttpServletRequest httpRequest) {
        String refreshToken = jwtManager.parseJwt(httpRequest);

        String email = jwtManager.getEmailFromJwtToken(refreshToken);
        String accessToken = jwtManager.generateAccessToken(email);

        log.info("Access token refreshed for {}", email);
        return new AuthTokensResponse(accessToken, refreshToken);
    }

    @Transactional
    public void logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

}

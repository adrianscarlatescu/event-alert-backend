package com.as.eventalertbackend.service;

import com.as.eventalertbackend.AppProperties;
import com.as.eventalertbackend.dto.request.AuthLoginRequest;
import com.as.eventalertbackend.dto.request.AuthRegisterRequest;
import com.as.eventalertbackend.dto.response.AuthTokensResponse;
import com.as.eventalertbackend.dto.response.UserResponse;
import com.as.eventalertbackend.model.RoleCode;
import com.as.eventalertbackend.error.ApiErrorMessage;
import com.as.eventalertbackend.error.exception.InvalidActionException;
import com.as.eventalertbackend.persistence.entity.User;
import com.as.eventalertbackend.persistence.entity.Role;
import com.as.eventalertbackend.security.jwt.JwtManager;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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
import java.util.List;

@Service
@Transactional
@Slf4j
public class AuthService {

    private final ModelMapper mapper;
    private final AppProperties appProperties;

    private final UserService userService;
    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtManager jwtManager;

    @Autowired
    public AuthService(ModelMapper mapper,
                       AppProperties appProperties,
                       UserService userService,
                       RoleService roleService,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtManager jwtManager) {
        this.mapper = mapper;
        this.appProperties = appProperties;
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtManager = jwtManager;
    }

    public UserResponse register(AuthRegisterRequest registerRequest) {
        String email = registerRequest.getEmail();
        String password = registerRequest.getPassword();
        String confirmPassword = registerRequest.getConfirmPassword();

        boolean emailExists = userService.existsByEmail(email);
        if (emailExists) {
            throw new InvalidActionException(ApiErrorMessage.ACCOUNT_ALREADY_CREATED);
        }

        if (!password.equals(confirmPassword)) {
            throw new InvalidActionException(ApiErrorMessage.PASSWORDS_NOT_MATCH);
        }

        Role role = roleService.findEntityByCode(RoleCode.BASIC);

        User user = new User(email,
                passwordEncoder.encode(confirmPassword),
                List.of(role));

        return mapper.map(userService.saveEntity(user), UserResponse.class);
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
        String authHeader = httpRequest.getHeader(appProperties.getSecurity().getAuthHeader());
        String refreshToken = jwtManager.parseJwt(authHeader);

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

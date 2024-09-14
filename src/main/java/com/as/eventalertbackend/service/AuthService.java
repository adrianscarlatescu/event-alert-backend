package com.as.eventalertbackend.service;

import com.as.eventalertbackend.controller.request.AuthLoginRequestDto;
import com.as.eventalertbackend.controller.request.AuthRegisterRequestDto;
import com.as.eventalertbackend.controller.response.AuthRefreshTokenResponseDto;
import com.as.eventalertbackend.controller.response.AuthTokensResponseDto;
import com.as.eventalertbackend.data.model.User;
import com.as.eventalertbackend.data.model.UserRole;
import com.as.eventalertbackend.enums.Role;
import com.as.eventalertbackend.handler.exception.InvalidActionException;
import com.as.eventalertbackend.security.jwt.JwtUtils;
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
@Slf4j
public class AuthService {

    private final UserService userService;
    private final UserRoleService userRoleService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Autowired
    public AuthService(UserService userService,
                       UserRoleService userRoleService,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtUtils jwtUtils) {
        this.userService = userService;
        this.userRoleService = userRoleService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    public User register(AuthRegisterRequestDto registerRequestDto) {
        boolean emailExists = userService.existsByEmail(registerRequestDto.getEmail());
        if (emailExists) {
            throw new InvalidActionException("The account is already created");
        }

        if (!registerRequestDto.getPassword().equals(registerRequestDto.getConfirmPassword())) {
            throw new InvalidActionException("The passwords are not identical");
        }

        UserRole role = userRoleService.findByName(Role.ROLE_USER);

        User user = new User(registerRequestDto.getEmail(),
                passwordEncoder.encode(registerRequestDto.getPassword()),
                Collections.singleton(role));

        return userService.save(user);
    }

    @Transactional
    public AuthTokensResponseDto login(AuthLoginRequestDto loginRequestDto) {
        String email = loginRequestDto.getEmail();
        String password = loginRequestDto.getPassword();

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtils.generateAccessToken(email);
        String refreshToken = jwtUtils.generateRefreshToken(email);

        return new AuthTokensResponseDto(accessToken, refreshToken);
    }

    public AuthRefreshTokenResponseDto refreshToken(HttpServletRequest request) {
        String token = jwtUtils.parseJwt(request);
        if (token == null || !jwtUtils.validateJwtToken(token) || !jwtUtils.isRefreshToken(token)) {
            throw new InvalidActionException("Invalid token");
        }

        String email = jwtUtils.getEmailFromJwtToken(token);
        String accessToken = jwtUtils.generateAccessToken(email);

        log.info("New access token generated for user with email: {}, access token: {}", email, accessToken);
        return new AuthRefreshTokenResponseDto(accessToken);
    }

    @Transactional
    public void logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

}

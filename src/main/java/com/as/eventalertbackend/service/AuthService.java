package com.as.eventalertbackend.service;

import com.as.eventalertbackend.controller.request.AuthLoginBody;
import com.as.eventalertbackend.controller.request.AuthRegisterBody;
import com.as.eventalertbackend.controller.response.AuthRefreshTokenResponse;
import com.as.eventalertbackend.controller.response.AuthTokensResponse;
import com.as.eventalertbackend.data.model.User;
import com.as.eventalertbackend.data.model.UserRole;
import com.as.eventalertbackend.enums.Role;
import com.as.eventalertbackend.handler.exception.IllegalActionException;
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

    public User register(AuthRegisterBody body) {
        boolean isEmail = userService.existsByEmail(body.getEmail());
        if (isEmail) {
            throw new IllegalActionException(
                    "Email already registered " + body.getEmail(),
                    "The account is already created");
        }

        if (!body.getPassword().equals(body.getConfirmPassword())) {
            throw new IllegalActionException(
                    "Passwords don't match " + body.getEmail(),
                    "The passwords are not identical");
        }

        UserRole role = userRoleService.findByName(Role.ROLE_USER);

        User user = new User(body.getEmail(),
                passwordEncoder.encode(body.getPassword()),
                Collections.singleton(role));

        return userService.save(user);
    }

    @Transactional
    public AuthTokensResponse login(AuthLoginBody body) {
        String email = body.getEmail();
        String password = body.getPassword();

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtils.generateAccessToken(email);
        String refreshToken = jwtUtils.generateRefreshToken(email);

        return new AuthTokensResponse(accessToken, refreshToken);
    }

    public AuthRefreshTokenResponse refreshToken(HttpServletRequest request) {
        String token = jwtUtils.parseJwt(request);
        if (token == null || !jwtUtils.validateJwtToken(token) || !jwtUtils.isRefreshToken(token)) {
            throw new IllegalActionException("Invalid token: " + token, "Invalid token");
        }

        String email = jwtUtils.getEmailFromJwtToken(token);
        String accessToken = jwtUtils.generateAccessToken(email);

        log.info("New access token generated for user with email: {}, access token: {}", email, accessToken);
        return new AuthRefreshTokenResponse(accessToken);
    }

    @Transactional
    public void logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

}

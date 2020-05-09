package com.as.eventalertbackend.service;

import com.as.eventalertbackend.controller.request.AuthLoginBody;
import com.as.eventalertbackend.controller.request.AuthRegisterBody;
import com.as.eventalertbackend.controller.response.AuthRefreshTokenResponse;
import com.as.eventalertbackend.controller.response.AuthTokensResponse;
import com.as.eventalertbackend.data.model.AuthRefreshToken;
import com.as.eventalertbackend.data.model.User;
import com.as.eventalertbackend.data.model.UserRole;
import com.as.eventalertbackend.data.reopsitory.AuthRefreshTokenRepository;
import com.as.eventalertbackend.enums.Role;
import com.as.eventalertbackend.handler.exception.IllegalActionException;
import com.as.eventalertbackend.handler.exception.RecordNotFoundException;
import com.as.eventalertbackend.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

@Service
public class AuthService {

    private final UserService userService;
    private final UserRoleService userRoleService;
    private final AuthRefreshTokenRepository refreshTokenRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Autowired
    public AuthService(UserService userService,
                       UserRoleService userRoleService,
                       BCryptPasswordEncoder bCryptPasswordEncoder,
                       AuthRefreshTokenRepository refreshTokenRepository,
                       AuthenticationManager authenticationManager,
                       JwtUtils jwtUtils) {
        this.userService = userService;
        this.userRoleService = userRoleService;
        this.refreshTokenRepository = refreshTokenRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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
                bCryptPasswordEncoder.encode(body.getPassword()),
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

        User user = userService.findByEmail(email);
        refreshTokenRepository.deleteByUserId(user.getId());

        AuthRefreshToken model = new AuthRefreshToken(refreshToken, user);
        refreshTokenRepository.save(model);

        return new AuthTokensResponse(accessToken, refreshToken);
    }

    public AuthRefreshTokenResponse refreshToken(HttpServletRequest request) {
        String token = jwtUtils.parseJwt(request);
        if (!refreshTokenRepository.existsByRefreshToken(token)) {
            throw new RecordNotFoundException(
                    "The refresh token doesn't exist " + token,
                    "Refresh token not found");
        }

        String email = jwtUtils.getEmailFromJwtToken(token);
        String accessToken = jwtUtils.generateAccessToken(email);

        return new AuthRefreshTokenResponse(accessToken);
    }

    @Transactional
    public void logout(Long userId) {
        refreshTokenRepository.deleteByUserId(userId);
        SecurityContextHolder.getContext().setAuthentication(null);
    }

}

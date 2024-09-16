package com.as.eventalertbackend.service;

import com.as.eventalertbackend.dto.request.AuthLoginRequestDto;
import com.as.eventalertbackend.dto.request.AuthRegisterRequestDto;
import com.as.eventalertbackend.dto.response.AuthTokensResponseDto;
import com.as.eventalertbackend.dto.response.UserResponseDto;
import com.as.eventalertbackend.enums.Role;
import com.as.eventalertbackend.handler.ApiErrorMessage;
import com.as.eventalertbackend.handler.exception.InvalidActionException;
import com.as.eventalertbackend.handler.exception.RecordNotFoundException;
import com.as.eventalertbackend.jpa.entity.User;
import com.as.eventalertbackend.jpa.entity.UserRole;
import com.as.eventalertbackend.jpa.reopsitory.UserRepository;
import com.as.eventalertbackend.jpa.reopsitory.UserRoleRepository;
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
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtManager jwtManager;

    @Autowired
    public AuthService(UserRepository userRepository,
                       UserRoleRepository userRoleRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtManager jwtManager) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtManager = jwtManager;
    }

    public UserResponseDto register(AuthRegisterRequestDto registerRequestDto) {
        boolean emailExists = userRepository.existsByEmail(registerRequestDto.getEmail());
        if (emailExists) {
            throw new InvalidActionException(ApiErrorMessage.ACCOUNT_ALREADY_CREATED);
        }

        if (!registerRequestDto.getPassword().equals(registerRequestDto.getConfirmPassword())) {
            throw new InvalidActionException(ApiErrorMessage.PASSWORDS_NOT_MATCH);
        }

        UserRole userRole = userRoleRepository.findByName(Role.ROLE_USER)
                .orElseThrow(() -> new RecordNotFoundException(ApiErrorMessage.ROLE_NOT_FOUND));

        User user = new User(registerRequestDto.getEmail(),
                passwordEncoder.encode(registerRequestDto.getPassword()),
                Collections.singleton(userRole));

        return userRepository.save(user).toDto();
    }

    @Transactional
    public AuthTokensResponseDto login(AuthLoginRequestDto loginDto) {
        String email = loginDto.getEmail();
        String password = loginDto.getPassword();

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtManager.generateAccessToken(email);
        String refreshToken = jwtManager.generateRefreshToken(email);

        return new AuthTokensResponseDto(accessToken, refreshToken);
    }

    public AuthTokensResponseDto refreshToken(HttpServletRequest request) {
        String refreshToken = jwtManager.parseJwt(request);
        if (refreshToken == null || !jwtManager.validateJwtToken(refreshToken) || !jwtManager.isRefreshToken(refreshToken)) {
            throw new InvalidActionException(ApiErrorMessage.INVALID_REFRESH_TOKEN);
        }

        String email = jwtManager.getEmailFromJwtToken(refreshToken);
        String accessToken = jwtManager.generateAccessToken(email);

        log.info("New access token generated for user with email: {}, access token: {}", email, accessToken);
        return new AuthTokensResponseDto(accessToken, refreshToken);
    }

    @Transactional
    public void logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

}

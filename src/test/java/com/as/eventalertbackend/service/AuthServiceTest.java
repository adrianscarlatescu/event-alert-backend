package com.as.eventalertbackend.service;

import com.as.eventalertbackend.controller.request.AuthLoginRequestDto;
import com.as.eventalertbackend.controller.request.AuthRegisterRequestDto;
import com.as.eventalertbackend.controller.response.AuthRefreshTokenResponseDto;
import com.as.eventalertbackend.controller.response.AuthTokensResponseDto;
import com.as.eventalertbackend.data.model.EventSeverity;
import com.as.eventalertbackend.data.model.User;
import com.as.eventalertbackend.data.model.UserRole;
import com.as.eventalertbackend.enums.Role;
import com.as.eventalertbackend.handler.exception.InvalidActionException;
import com.as.eventalertbackend.security.jwt.JwtUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserService userService;
    @Mock
    private UserRoleService userRoleService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtUtils jwtUtils;

    @InjectMocks
    private AuthService authService;

    @Test
    public void shouldNotRegisterExistingEmail() {
        // given
        String email = "test@test.com";
        AuthRegisterRequestDto registerRequestDto = new AuthRegisterRequestDto();
        registerRequestDto.setEmail(email);

        given(userService.existsByEmail(email)).willReturn(true);

        // when
        // then
        assertThrows(InvalidActionException.class, () -> authService.register(registerRequestDto));
        verify(userService, times(0)).save(any());
    }

    @Test
    public void shouldNotRegisterNotMatchingPasswords() {
        // given
        String email = "test@test.com";
        AuthRegisterRequestDto registerRequestDto = new AuthRegisterRequestDto();
        registerRequestDto.setEmail(email);
        registerRequestDto.setPassword("password1");
        registerRequestDto.setConfirmPassword("password2");

        given(userService.existsByEmail(email)).willReturn(false);

        // when
        // then
        assertThrows(InvalidActionException.class, () -> authService.register(registerRequestDto));
        verify(userService, times(0)).save(any());
    }

    @Test
    public void shouldRegister() {
        // given
        String email = "test@test.com";
        String password = "test";
        String encodedPassword = "encodedPassword";

        AuthRegisterRequestDto registerRequestDto = new AuthRegisterRequestDto();
        registerRequestDto.setEmail(email);
        registerRequestDto.setPassword(password);
        registerRequestDto.setConfirmPassword(password);

        UserRole mockUserRole = new UserRole();
        mockUserRole.setName(Role.ROLE_USER);

        User mockUser = new User(email, encodedPassword, Collections.singleton(mockUserRole));

        given(userService.existsByEmail(email)).willReturn(false);
        given(userRoleService.findByName(Role.ROLE_USER)).willReturn(mockUserRole);
        given(passwordEncoder.encode(password)).willReturn(encodedPassword);
        given(userService.save(any())).willReturn(mockUser);

        // when
        User user = authService.register(registerRequestDto);

        // then
        ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userService).save(argumentCaptor.capture());

        User capturedUser = argumentCaptor.getValue();

        assertEquals(email, capturedUser.getEmail());
        assertEquals(encodedPassword, capturedUser.getPassword());
        assertTrue(capturedUser.getUserRoles().stream().map(UserRole::getName).anyMatch(role -> role == Role.ROLE_USER));
    }

    @Test
    public void shouldNotLogin() {
        // given
        String email = "test@test.com";
        String password = "test";

        AuthLoginRequestDto loginRequestDto = new AuthLoginRequestDto();
        loginRequestDto.setEmail(email);
        loginRequestDto.setPassword(password);

        given(authenticationManager.authenticate(any())).willThrow(IllegalArgumentException.class);

        // when
        // then
        assertThrows(IllegalArgumentException.class, () -> authService.login(loginRequestDto));
    }

    @Test
    public void shouldLogin() {
        // given
        String email = "test@test.com";
        String password = "test";

        AuthLoginRequestDto loginRequestDto = new AuthLoginRequestDto();
        loginRequestDto.setEmail(email);
        loginRequestDto.setPassword(password);

        given(authenticationManager.authenticate(any())).willReturn(new UsernamePasswordAuthenticationToken(email, password));
        given(jwtUtils.generateAccessToken(email)).willReturn("accessToken");
        given(jwtUtils.generateRefreshToken(email)).willReturn("refreshToken");

        // when
        AuthTokensResponseDto response = authService.login(loginRequestDto);

        // then
        assertNotNull(response.getAccessToken());
        assertNotNull(response.getAccessToken());
    }

    @Test
    public void shouldNotRefreshToken() {
        // given
        String refreshToken = "refreshToken";
        given(jwtUtils.parseJwt(any())).willReturn(refreshToken);
        given(jwtUtils.validateJwtToken(refreshToken)).willReturn(false);

        // when
        // then
        assertThrows(InvalidActionException.class, () -> authService.refreshToken(any()));
    }

    @Test
    public void shouldRefreshToken() {
        // given
        String accessToken = "accessToken";
        String refreshToken = "refreshToken";
        String email = "email";

        given(jwtUtils.parseJwt(any())).willReturn(refreshToken);
        given(jwtUtils.validateJwtToken(refreshToken)).willReturn(true);
        given(jwtUtils.isRefreshToken(refreshToken)).willReturn(true);
        given(jwtUtils.getEmailFromJwtToken(refreshToken)).willReturn(email);
        given(jwtUtils.generateAccessToken(email)).willReturn(accessToken);

        // when
        AuthRefreshTokenResponseDto refreshTokenResponseDto = authService.refreshToken(any());

        // then
        assertNotNull(refreshTokenResponseDto);
        assertNotNull(refreshTokenResponseDto.getAccessToken());
    }

}

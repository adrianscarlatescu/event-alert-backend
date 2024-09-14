package com.as.eventalertbackend.controller;

import com.as.eventalertbackend.controller.request.AuthLoginRequestDto;
import com.as.eventalertbackend.controller.request.AuthRegisterRequestDto;
import com.as.eventalertbackend.controller.response.AuthRefreshTokenResponseDto;
import com.as.eventalertbackend.controller.response.AuthTokensResponseDto;
import com.as.eventalertbackend.data.model.User;
import com.as.eventalertbackend.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AuthController.class)
class AuthControllerTest extends AbstractControllerTest {

    @MockBean
    private AuthService authService;

    private static final String AUTH_PATH = "/auth";

    private final Long id = 1L;
    private final String email = "test@test.com";
    private final String password = "password";

    @Test
    public void shouldRegister() throws Exception {
        // given
        AuthRegisterRequestDto registerRequestDto = new AuthRegisterRequestDto();
        registerRequestDto.setEmail(email);
        registerRequestDto.setPassword(password);
        registerRequestDto.setConfirmPassword(password);
        String jsonBody = objectMapper.writeValueAsString(registerRequestDto);

        User user = getMockUser();

        given(authService.register(registerRequestDto)).willReturn(user);

        // when
        // then
        mockMvc.perform(post(AUTH_PATH + "/register").headers(httpHeaders).content(jsonBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(id.intValue())))
                .andExpect(jsonPath("$.email", is(email)))
                .andExpect(jsonPath("$.password").doesNotExist());
    }

    @Test
    public void shouldNotRegisterForInvalidEmail() throws Exception {
        // given
        AuthRegisterRequestDto registerRequestDto = new AuthRegisterRequestDto();
        registerRequestDto.setEmail("email");
        registerRequestDto.setPassword(password);
        registerRequestDto.setConfirmPassword(password);
        String jsonBody = objectMapper.writeValueAsString(registerRequestDto);

        User user = getMockUser();

        given(authService.register(registerRequestDto)).willReturn(user);

        // when
        // then
        mockMvc.perform(post(AUTH_PATH + "/register").headers(httpHeaders).content(jsonBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Invalid email")));
    }

    @Test
    public void shouldLogin() throws Exception {
        // given
        String accessToken = "accessToken";
        String refreshToken = "refreshToken";

        AuthLoginRequestDto loginRequestDto = new AuthLoginRequestDto();
        loginRequestDto.setEmail(email);
        loginRequestDto.setPassword(password);
        String jsonBody = objectMapper.writeValueAsString(loginRequestDto);

        AuthTokensResponseDto tokensResponseDto = new AuthTokensResponseDto(accessToken, refreshToken);

        given(authService.login(loginRequestDto)).willReturn(tokensResponseDto);

        // when
        // then
        mockMvc.perform(post(AUTH_PATH + "/login").headers(httpHeaders).content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken", is(accessToken)))
                .andExpect(jsonPath("$.refreshToken", is(refreshToken)));
    }

    @Test
    public void shouldNotLoginForEmptyEmail() throws Exception {
        // given
        AuthLoginRequestDto loginRequestDto = new AuthLoginRequestDto();
        loginRequestDto.setEmail("");
        loginRequestDto.setPassword(password);
        String jsonBody = objectMapper.writeValueAsString(loginRequestDto);

        // when
        // then
        mockMvc.perform(post(AUTH_PATH + "/login").headers(httpHeaders).content(jsonBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("The email is mandatory")));
    }

    @Test
    public void shouldRefreshToken() throws Exception {
        // given
        String accessToken = "accessToken";
        AuthRefreshTokenResponseDto tokenResponseDto = new AuthRefreshTokenResponseDto(accessToken);

        given(authService.refreshToken(any())).willReturn(tokenResponseDto);

        // when
        // then
        mockMvc.perform(get(AUTH_PATH + "/refresh").headers(httpHeaders))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken", is(accessToken)));
    }

    private User getMockUser() {
        User user = new User();
        user.setId(id);
        user.setEmail(email);
        user.setPassword(password);
        user.setJoinDateTime(LocalDateTime.now());
        return user;
    }

}

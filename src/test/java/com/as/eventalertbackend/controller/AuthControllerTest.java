package com.as.eventalertbackend.controller;

import com.as.eventalertbackend.controller.request.AuthLoginBody;
import com.as.eventalertbackend.controller.request.AuthRegisterBody;
import com.as.eventalertbackend.controller.response.AuthRefreshTokenResponse;
import com.as.eventalertbackend.controller.response.AuthTokensResponse;
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
        AuthRegisterBody body = new AuthRegisterBody();
        body.setEmail(email);
        body.setPassword(password);
        body.setConfirmPassword(password);
        String jsonBody = objectMapper.writeValueAsString(body);

        User user = getMockUser();

        given(authService.register(any())).willReturn(user);

        // when
        // then
        mockMvc.perform(post(AUTH_PATH + "/register").headers(httpHeaders).content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id.intValue())))
                .andExpect(jsonPath("$.email", is(email)))
                .andExpect(jsonPath("$.password").doesNotExist());
    }

    @Test
    public void shouldNotRegisterForInvalidEmail() throws Exception {
        // given
        AuthRegisterBody body = new AuthRegisterBody();
        body.setEmail("email");
        body.setPassword(password);
        body.setConfirmPassword(password);
        String jsonBody = objectMapper.writeValueAsString(body);

        User user = getMockUser();

        given(authService.register(any())).willReturn(user);

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

        AuthLoginBody body = new AuthLoginBody();
        body.setEmail(email);
        body.setPassword(password);
        String jsonBody = objectMapper.writeValueAsString(body);

        AuthTokensResponse tokensResponse = new AuthTokensResponse(accessToken, refreshToken);

        given(authService.login(any())).willReturn(tokensResponse);

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
        AuthLoginBody body = new AuthLoginBody();
        body.setEmail("");
        body.setPassword("");
        String jsonBody = objectMapper.writeValueAsString(body);

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
        AuthRefreshTokenResponse tokenResponse = new AuthRefreshTokenResponse(accessToken);

        given(authService.refreshToken(any())).willReturn(tokenResponse);

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

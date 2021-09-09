package com.as.eventalertbackend.controller;

import com.as.eventalertbackend.data.model.User;
import com.as.eventalertbackend.data.model.UserRole;
import com.as.eventalertbackend.enums.Role;
import com.as.eventalertbackend.security.jwt.JwtUtils;
import com.as.eventalertbackend.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.BDDMockito.given;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public abstract class AbstractControllerTest {

    @Autowired
    protected JwtUtils jwtUtils;
    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    protected UserService userService;

    protected User authUser;
    protected HttpHeaders httpHeaders;
    protected ObjectMapper objectMapper;

    @PostConstruct
    public void init() {
        objectMapper = new ObjectMapper();

        String authEmail = "admin@test.com";
        String accessToken = jwtUtils.generateAccessToken(authEmail);

        httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + accessToken);
        httpHeaders.add("Content-Type", "application/json");

        UserRole userRole = new UserRole();
        userRole.setName(Role.ROLE_ADMIN);

        Set<UserRole> userRoles = Collections.singleton(userRole);

        authUser = new User();
        authUser.setId(99L);
        authUser.setEmail(authEmail);
        authUser.setUserRoles(userRoles);

        given(userService.loadUserByUsername(authEmail)).willReturn(authUser);
    }

}

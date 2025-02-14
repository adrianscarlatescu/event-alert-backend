package com.as.eventalertbackend.controller;

import com.as.eventalertbackend.dto.request.AuthLoginRequest;
import com.as.eventalertbackend.dto.request.AuthRegisterRequest;
import com.as.eventalertbackend.dto.response.AuthTokensResponse;
import com.as.eventalertbackend.dto.response.UserResponse;
import com.as.eventalertbackend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody AuthRegisterRequest registerRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authService.register(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthTokensResponse> login(@Valid @RequestBody AuthLoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @GetMapping("/refresh")
    public ResponseEntity<AuthTokensResponse> refreshToken(HttpServletRequest httpRequest) {
        return ResponseEntity.ok(authService.refreshToken(httpRequest));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        authService.logout();
        return ResponseEntity.ok().build();
    }

}

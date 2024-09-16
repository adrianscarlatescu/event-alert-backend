package com.as.eventalertbackend.controller;

import com.as.eventalertbackend.dto.request.AuthLoginRequestDto;
import com.as.eventalertbackend.dto.request.AuthRegisterRequestDto;
import com.as.eventalertbackend.dto.response.AuthTokensResponseDto;
import com.as.eventalertbackend.dto.response.UserResponseDto;
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
    public ResponseEntity<UserResponseDto> register(@Valid @RequestBody AuthRegisterRequestDto registerRequestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authService.register(registerRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthTokensResponseDto> login(@Valid @RequestBody AuthLoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(authService.login(loginRequestDto));
    }

    @GetMapping("/refresh")
    public ResponseEntity<AuthTokensResponseDto> refreshToken(HttpServletRequest request) {
        return ResponseEntity.ok(authService.refreshToken(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        authService.logout();
        return ResponseEntity.ok().build();
    }

}

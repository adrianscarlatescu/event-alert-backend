package com.as.eventalertbackend.controller;

import com.as.eventalertbackend.dto.auth.AuthLoginDTO;
import com.as.eventalertbackend.dto.auth.AuthRegisterDTO;
import com.as.eventalertbackend.dto.auth.AuthTokensDTO;
import com.as.eventalertbackend.dto.user.UserDTO;
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
    public ResponseEntity<UserDTO> register(@Valid @RequestBody AuthRegisterDTO registerDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authService.register(registerDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthTokensDTO> login(@Valid @RequestBody AuthLoginDTO loginDTO) {
        return ResponseEntity.ok(authService.login(loginDTO));
    }

    @GetMapping("/refresh")
    public ResponseEntity<AuthTokensDTO> refreshToken(HttpServletRequest httpRequest) {
        return ResponseEntity.ok(authService.refreshToken(httpRequest));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        authService.logout();
        return ResponseEntity.ok().build();
    }

}

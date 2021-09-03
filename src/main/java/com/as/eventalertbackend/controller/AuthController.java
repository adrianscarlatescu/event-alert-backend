package com.as.eventalertbackend.controller;

import com.as.eventalertbackend.controller.request.AuthLoginBody;
import com.as.eventalertbackend.controller.request.AuthRegisterBody;
import com.as.eventalertbackend.controller.response.AuthRefreshTokenResponse;
import com.as.eventalertbackend.controller.response.AuthTokensResponse;
import com.as.eventalertbackend.data.model.User;
import com.as.eventalertbackend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService service;

    @Autowired
    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody AuthRegisterBody body) {
        return ResponseEntity.ok(service.register(body));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthTokensResponse> login(@Valid @RequestBody AuthLoginBody body) {
        return ResponseEntity.ok(service.login(body));
    }

    @GetMapping("/refresh")
    public ResponseEntity<AuthRefreshTokenResponse> refreshToken(HttpServletRequest request) {
        return ResponseEntity.ok(service.refreshToken(request));
    }

    @PostMapping("/logout")
    public void logout() {
        service.logout();
    }

}

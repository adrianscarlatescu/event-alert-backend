package com.as.eventalertbackend.dto.auth;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class AuthTokensDTO implements Serializable {

    private String accessToken;
    private String refreshToken;

    private AuthTokensDTO() {
    }

    public AuthTokensDTO(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

}

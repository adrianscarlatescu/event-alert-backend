package com.as.eventalertbackend.controller.response;

import lombok.Getter;

@Getter
public class AuthTokensResponse {

    private String accessToken;
    private String refreshToken;

    public AuthTokensResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

}

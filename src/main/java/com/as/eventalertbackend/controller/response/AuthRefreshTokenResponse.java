package com.as.eventalertbackend.controller.response;

public class AuthRefreshTokenResponse {

    private String accessToken;

    public AuthRefreshTokenResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
}

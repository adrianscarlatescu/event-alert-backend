package com.as.eventalertbackend.dto.response;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class AuthTokensResponse implements Serializable {

    private String accessToken;
    private String refreshToken;

    private AuthTokensResponse() {
    }

    public AuthTokensResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

}

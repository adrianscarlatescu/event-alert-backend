package com.as.eventalertbackend.dto.response;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class AuthTokensResponseDto implements Serializable {

    private String accessToken;
    private String refreshToken;

    private AuthTokensResponseDto() {
    }

    public AuthTokensResponseDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

}

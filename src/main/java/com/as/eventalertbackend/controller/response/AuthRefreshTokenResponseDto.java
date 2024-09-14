package com.as.eventalertbackend.controller.response;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class AuthRefreshTokenResponseDto implements Serializable {

    private String accessToken;

    private AuthRefreshTokenResponseDto() {
    }

    public AuthRefreshTokenResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }

}

package com.as.eventalertbackend.dto.response;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class AuthTokensDto implements Serializable {

    private String accessToken;
    private String refreshToken;

    private AuthTokensDto() {
    }

    public AuthTokensDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

}

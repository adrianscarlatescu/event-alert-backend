package com.as.eventalertbackend.dto.response;

import lombok.Getter;

@Getter
public class ApiErrorResponseDto {
    private String code;
    private String message;

    private ApiErrorResponseDto() {
    }

    public ApiErrorResponseDto(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

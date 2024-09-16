package com.as.eventalertbackend.dto.response;

import lombok.Getter;

@Getter
public class ApiErrorDto {

    private String code;
    private String message;

    private ApiErrorDto() {
    }

    public ApiErrorDto(String code, String message) {
        this.code = code;
        this.message = message;
    }

}

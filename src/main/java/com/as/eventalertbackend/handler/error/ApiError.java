package com.as.eventalertbackend.handler.error;

import lombok.Getter;

@Getter
public class ApiError {

    private String code;
    private String message;

    private ApiError() {
    }

    public ApiError(String code, String message) {
        this.code = code;
        this.message = message;
    }

}

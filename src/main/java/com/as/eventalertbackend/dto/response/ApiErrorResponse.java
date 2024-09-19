package com.as.eventalertbackend.dto.response;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class ApiErrorResponse implements Serializable {

    private String code;
    private String message;

    private ApiErrorResponse() {
    }

    public ApiErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

}

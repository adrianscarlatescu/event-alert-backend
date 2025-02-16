package com.as.eventalertbackend.dto.error;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class ApiErrorDTO implements Serializable {

    private String code;
    private String message;

    private ApiErrorDTO() {
    }

    public ApiErrorDTO(String code, String message) {
        this.code = code;
        this.message = message;
    }

}

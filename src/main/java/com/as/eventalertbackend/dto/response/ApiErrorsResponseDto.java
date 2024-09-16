package com.as.eventalertbackend.dto.response;

import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
public class ApiErrorsResponseDto {

    private List<ApiErrorResponseDto> errors;

    private ApiErrorsResponseDto() {
    }

    public ApiErrorsResponseDto(String code, String message) {
        this.errors = Collections.singletonList(new ApiErrorResponseDto(code, message));
    }

    public ApiErrorsResponseDto(List<ApiErrorResponseDto> errors) {
        this.errors = errors;
    }

}

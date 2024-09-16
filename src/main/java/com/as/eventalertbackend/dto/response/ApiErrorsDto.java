package com.as.eventalertbackend.dto.response;

import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
public class ApiErrorsDto {

    private List<ApiErrorDto> errors;

    private ApiErrorsDto() {
    }

    public ApiErrorsDto(String code, String message) {
        this.errors = Collections.singletonList(new ApiErrorDto(code, message));
    }

    public ApiErrorsDto(List<ApiErrorDto> errors) {
        this.errors = errors;
    }

}

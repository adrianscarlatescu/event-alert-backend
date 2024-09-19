package com.as.eventalertbackend.dto.response;

import lombok.Getter;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Getter
public class ApiErrorsResponse implements Serializable {

    private List<ApiErrorResponse> errors;

    private ApiErrorsResponse() {
    }

    public ApiErrorsResponse(String code, String message) {
        this.errors = Collections.singletonList(new ApiErrorResponse(code, message));
    }

    public ApiErrorsResponse(List<ApiErrorResponse> errors) {
        this.errors = errors;
    }

}

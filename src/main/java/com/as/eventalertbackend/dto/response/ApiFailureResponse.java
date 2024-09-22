package com.as.eventalertbackend.dto.response;

import lombok.Getter;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Getter
public class ApiFailureResponse implements Serializable {

    private List<ApiErrorResponse> errors;

    private ApiFailureResponse() {
    }

    public ApiFailureResponse(String code, String message) {
        this.errors = Collections.singletonList(new ApiErrorResponse(code, message));
    }

    public ApiFailureResponse(List<ApiErrorResponse> errors) {
        this.errors = errors;
    }

}

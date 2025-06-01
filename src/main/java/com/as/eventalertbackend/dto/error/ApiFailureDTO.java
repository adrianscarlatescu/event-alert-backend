package com.as.eventalertbackend.dto.error;

import lombok.Getter;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Getter
public class ApiFailureDTO implements Serializable {

    private List<ApiErrorDTO> errors;

    private ApiFailureDTO() {
    }

    public ApiFailureDTO(String code, String message) {
        this.errors = Collections.singletonList(new ApiErrorDTO(code, message));
    }

    public ApiFailureDTO(List<ApiErrorDTO> errors) {
        this.errors = errors;
    }

}

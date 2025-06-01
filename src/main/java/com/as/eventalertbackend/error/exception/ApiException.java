package com.as.eventalertbackend.error.exception;

import com.as.eventalertbackend.error.ApiErrorMessage;
import lombok.Getter;

@Getter
public abstract class ApiException extends RuntimeException {

    public ApiException(ApiErrorMessage apiErrorMessage) {
        super(apiErrorMessage.getValue());
    }

}

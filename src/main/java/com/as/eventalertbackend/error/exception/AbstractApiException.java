package com.as.eventalertbackend.error.exception;

import com.as.eventalertbackend.error.ApiErrorMessage;
import lombok.Getter;

@Getter
public abstract class AbstractApiException extends RuntimeException {

    public AbstractApiException(ApiErrorMessage apiErrorMessage) {
        super(apiErrorMessage.getValue());
    }

}

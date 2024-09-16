package com.as.eventalertbackend.handler.exception;

import com.as.eventalertbackend.handler.ApiErrorMessage;
import lombok.Getter;

@Getter
public abstract class AbstractApiException extends RuntimeException {

    public AbstractApiException(ApiErrorMessage apiErrorMessage) {
        super(apiErrorMessage.getValue());
    }

}

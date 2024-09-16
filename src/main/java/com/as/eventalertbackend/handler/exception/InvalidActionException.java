package com.as.eventalertbackend.handler.exception;

import com.as.eventalertbackend.handler.ApiErrorMessage;

public class InvalidActionException extends AbstractApiException {

    public InvalidActionException(ApiErrorMessage apiErrorMessage) {
        super(apiErrorMessage);
    }

}

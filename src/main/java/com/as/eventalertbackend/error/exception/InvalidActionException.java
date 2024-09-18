package com.as.eventalertbackend.error.exception;

import com.as.eventalertbackend.error.ApiErrorMessage;

public class InvalidActionException extends AbstractApiException {

    public InvalidActionException(ApiErrorMessage apiErrorMessage) {
        super(apiErrorMessage);
    }

}

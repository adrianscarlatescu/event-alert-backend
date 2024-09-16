package com.as.eventalertbackend.handler.exception;

import com.as.eventalertbackend.handler.ApiErrorMessage;

public class StorageFailException extends AbstractApiException {

    public StorageFailException(ApiErrorMessage apiErrorMessage) {
        super(apiErrorMessage);
    }

}

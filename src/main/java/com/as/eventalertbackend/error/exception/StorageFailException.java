package com.as.eventalertbackend.error.exception;

import com.as.eventalertbackend.error.ApiErrorMessage;

public class StorageFailException extends ApiException {

    public StorageFailException(ApiErrorMessage apiErrorMessage) {
        super(apiErrorMessage);
    }

}

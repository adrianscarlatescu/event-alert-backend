package com.as.eventalertbackend.error.exception;

import com.as.eventalertbackend.error.ApiErrorMessage;

public class RecordNotFoundException extends ApiException {

    public RecordNotFoundException(ApiErrorMessage apiErrorMessage) {
        super(apiErrorMessage);
    }

}

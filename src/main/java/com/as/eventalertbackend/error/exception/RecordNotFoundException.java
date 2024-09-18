package com.as.eventalertbackend.error.exception;

import com.as.eventalertbackend.error.ApiErrorMessage;

public class RecordNotFoundException extends AbstractApiException {

    public RecordNotFoundException(ApiErrorMessage apiErrorMessage) {
        super(apiErrorMessage);
    }

}

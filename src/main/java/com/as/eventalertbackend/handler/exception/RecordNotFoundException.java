package com.as.eventalertbackend.handler.exception;

import com.as.eventalertbackend.handler.ApiErrorMessage;

public class RecordNotFoundException extends AbstractApiException {

    public RecordNotFoundException(ApiErrorMessage apiErrorMessage) {
        super(apiErrorMessage);
    }

}

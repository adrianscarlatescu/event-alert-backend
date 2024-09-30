package com.as.eventalertbackend.error.exception;

import com.as.eventalertbackend.error.ApiErrorMessage;

public class ResourceNotFoundException extends AbstractApiException {

    public ResourceNotFoundException(ApiErrorMessage apiErrorMessage) {
        super(apiErrorMessage);
    }

}

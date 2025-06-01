package com.as.eventalertbackend.error.exception;

import com.as.eventalertbackend.error.ApiErrorMessage;

public class ResourceNotFoundException extends ApiException {

    public ResourceNotFoundException(ApiErrorMessage apiErrorMessage) {
        super(apiErrorMessage);
    }

}

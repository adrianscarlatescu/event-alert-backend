package com.as.eventalertbackend.handler.error;

import java.util.ArrayList;
import java.util.List;

public class FailureResponse {

    private List<ApiError> errors;

    public FailureResponse() {
        errors = new ArrayList<>();
    }

    public FailureResponse(String code, List<String> messages) {
        this();
        messages.forEach(message -> errors.add(new ApiError(code, message)));
    }

    public List<ApiError> getErrors() {
        return errors;
    }

}

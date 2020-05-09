package com.as.eventalertbackend.handler.exception;

import java.util.Collections;
import java.util.List;

public abstract class AbstractApiException extends RuntimeException {

    private List<String> responseDescriptions;

    public AbstractApiException(String message, String description) {
        super(message);
        this.responseDescriptions = Collections.singletonList(description);
    }

    public AbstractApiException(String message, List<String> responseDescriptions) {
        super(message);
        this.responseDescriptions = responseDescriptions;
    }

    public List<String> getResponseDescriptions() {
        return responseDescriptions;
    }

}

package com.as.eventalertbackend.handler.exception;

import lombok.Getter;

@Getter
public abstract class AbstractApiException extends RuntimeException {

    public AbstractApiException(String message) {
        super(message);
    }

}

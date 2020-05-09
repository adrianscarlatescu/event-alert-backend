package com.as.eventalertbackend.handler.exception;

import java.util.List;

public class RecordNotFoundException extends AbstractApiException {

    public RecordNotFoundException(String message, String responseDescription) {
        super(message, responseDescription);
    }

    public RecordNotFoundException(String message, List<String> responseDescriptions) {
        super(message, responseDescriptions);
    }

}
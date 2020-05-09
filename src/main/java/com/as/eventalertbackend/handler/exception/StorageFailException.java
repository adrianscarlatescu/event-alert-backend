package com.as.eventalertbackend.handler.exception;

import java.util.List;

public class StorageFailException extends AbstractApiException {

    public StorageFailException(String message, String responseDescription) {
        super(message, responseDescription);
    }

    public StorageFailException(String message, List<String> responseDescriptions) {
        super(message, responseDescriptions);
    }

}

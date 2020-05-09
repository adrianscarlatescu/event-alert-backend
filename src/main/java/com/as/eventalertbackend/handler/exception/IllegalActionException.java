package com.as.eventalertbackend.handler.exception;

import java.util.List;

public class IllegalActionException extends AbstractApiException {

    public IllegalActionException(String message, String responseDescription) {
        super(message, responseDescription);
    }

    public IllegalActionException(String message, List<String> responseDescriptions) {
        super(message, responseDescriptions);
    }

}

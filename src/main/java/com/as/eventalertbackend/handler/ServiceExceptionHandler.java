package com.as.eventalertbackend.handler;

import com.as.eventalertbackend.handler.error.FailureResponse;
import com.as.eventalertbackend.handler.exception.IllegalActionException;
import com.as.eventalertbackend.handler.exception.RecordNotFoundException;
import com.as.eventalertbackend.handler.exception.StorageFailException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class ServiceExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<FailureResponse> handle(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        List<String> responseDescriptions = e.getBindingResult().getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());
        FailureResponse body = new FailureResponse("ArgumentNotValid", responseDescriptions);
        return ResponseEntity
                .badRequest()
                .body(body);
    }

    @ExceptionHandler({IllegalActionException.class})
    public ResponseEntity<FailureResponse> handle(IllegalActionException e) {
        log.error(e.getMessage(), e);
        FailureResponse body = new FailureResponse("IllegalAction", e.getResponseDescriptions());
        return ResponseEntity
                .badRequest()
                .body(body);
    }

    @ExceptionHandler({RecordNotFoundException.class})
    public ResponseEntity<FailureResponse> handle(RecordNotFoundException e) {
        log.error(e.getMessage(), e);
        FailureResponse body = new FailureResponse("RecordNotFound", e.getResponseDescriptions());
        return ResponseEntity
                .badRequest()
                .body(body);
    }

    @ExceptionHandler({StorageFailException.class})
    public ResponseEntity<FailureResponse> handle(StorageFailException e) {
        log.error(e.getMessage(), e);
        FailureResponse body = new FailureResponse("StorageFail", e.getResponseDescriptions());
        return ResponseEntity
                .badRequest()
                .body(body);
    }

}

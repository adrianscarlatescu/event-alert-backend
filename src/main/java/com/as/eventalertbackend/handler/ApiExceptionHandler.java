package com.as.eventalertbackend.handler;

import com.as.eventalertbackend.handler.error.ApiError;
import com.as.eventalertbackend.handler.exception.InvalidActionException;
import com.as.eventalertbackend.handler.exception.RecordNotFoundException;
import com.as.eventalertbackend.handler.exception.StorageFailException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Slf4j
public class ApiExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ApiError> handle(Exception e, HttpServletRequest request) {
        logException(e, request);
        ApiError apiError = new ApiError("TechnicalException", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(apiError);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ApiError> handle(MethodArgumentNotValidException e, HttpServletRequest request) {
        logException(e, request);
        String message;
        if (e.getBindingResult().getFieldError() != null) {
            message = e.getBindingResult().getFieldError().getDefaultMessage();
        } else {
            message = HttpStatus.BAD_REQUEST.getReasonPhrase();
        }
        ApiError apiError = new ApiError("FieldValidation", message);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(apiError);
    }

    @ExceptionHandler({InvalidActionException.class})
    public ResponseEntity<ApiError> handle(InvalidActionException e, HttpServletRequest request) {
        logException(e, request);
        ApiError apiError = new ApiError("InvalidAction", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(apiError);
    }

    @ExceptionHandler({RecordNotFoundException.class})
    public ResponseEntity<ApiError> handle(RecordNotFoundException e, HttpServletRequest request) {
        logException(e, request);
        ApiError apiError = new ApiError("RecordNotFound", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(apiError);
    }

    @ExceptionHandler({StorageFailException.class})
    public ResponseEntity<ApiError> handle(StorageFailException e, HttpServletRequest request) {
        logException(e, request);
        ApiError apiError = new ApiError("StorageFail", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(apiError);
    }

    private void logException(Exception e, HttpServletRequest request) {
        log.error("{} {}", request.getMethod(), request.getRequestURI(), e);
    }

}

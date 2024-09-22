package com.as.eventalertbackend.error;

import com.as.eventalertbackend.dto.response.ApiErrorResponse;
import com.as.eventalertbackend.dto.response.ApiFailureResponse;
import com.as.eventalertbackend.error.exception.InvalidActionException;
import com.as.eventalertbackend.error.exception.RecordNotFoundException;
import com.as.eventalertbackend.error.exception.ResourceNotFoundException;
import com.as.eventalertbackend.error.exception.StorageFailException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class ApiExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ApiFailureResponse> handle(Exception e, HttpServletRequest request) {
        logException(e, request);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiFailureResponse(ApiErrorCode.TECHNICAL_ERROR.getValue(), e.getMessage()));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ApiFailureResponse> handle(MethodArgumentNotValidException e, HttpServletRequest request) {
        logException(e, request);

        List<ApiErrorResponse> errors = e.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new ApiErrorResponse(ApiErrorCode.FIELD_CONSTRAINT.getValue(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiFailureResponse(errors));
    }

    @ExceptionHandler({InvalidActionException.class})
    public ResponseEntity<ApiFailureResponse> handle(InvalidActionException e, HttpServletRequest request) {
        logException(e, request);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiFailureResponse(ApiErrorCode.INVALID_ACTION.getValue(), e.getMessage()));
    }

    @ExceptionHandler({RecordNotFoundException.class})
    public ResponseEntity<ApiFailureResponse> handle(RecordNotFoundException e, HttpServletRequest request) {
        logException(e, request);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiFailureResponse(ApiErrorCode.RECORD_NOT_FOUND.getValue(), e.getMessage()));
    }

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<ApiFailureResponse> handle(ResourceNotFoundException e, HttpServletRequest request) {
        logException(e, request);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiFailureResponse(ApiErrorCode.RESOURCE_NOT_FOUND.getValue(), e.getMessage()));
    }

    @ExceptionHandler({StorageFailException.class})
    public ResponseEntity<ApiFailureResponse> handle(StorageFailException e, HttpServletRequest request) {
        logException(e, request);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiFailureResponse(ApiErrorCode.STORAGE_FAIL.getValue(), e.getMessage()));
    }

    private void logException(Exception e, HttpServletRequest request) {
        log.error("{} {}", request.getMethod(), request.getRequestURI(), e);
    }

}

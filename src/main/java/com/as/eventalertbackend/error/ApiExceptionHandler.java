package com.as.eventalertbackend.error;

import com.as.eventalertbackend.dto.response.ApiErrorDto;
import com.as.eventalertbackend.dto.response.ApiErrorsDto;
import com.as.eventalertbackend.error.exception.InvalidActionException;
import com.as.eventalertbackend.error.exception.RecordNotFoundException;
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
    public ResponseEntity<ApiErrorsDto> handle(Exception e, HttpServletRequest request) {
        logException(e, request);

        ApiErrorsDto apiErrorsDto = new ApiErrorsDto(ApiErrorCode.TECHNICAL_ERROR.getValue(), e.getMessage());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(apiErrorsDto);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ApiErrorsDto> handle(MethodArgumentNotValidException e, HttpServletRequest request) {
        logException(e, request);

        List<ApiErrorDto> errors = e.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new ApiErrorDto(ApiErrorCode.FIELD_CONSTRAINT.getValue(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiErrorsDto(errors));
    }

    @ExceptionHandler({InvalidActionException.class})
    public ResponseEntity<ApiErrorsDto> handle(InvalidActionException e, HttpServletRequest request) {
        logException(e, request);

        ApiErrorsDto apiErrorsDto = new ApiErrorsDto(ApiErrorCode.INVALID_ACTION.getValue(), e.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(apiErrorsDto);
    }

    @ExceptionHandler({RecordNotFoundException.class})
    public ResponseEntity<ApiErrorsDto> handle(RecordNotFoundException e, HttpServletRequest request) {
        logException(e, request);

        ApiErrorsDto apiErrorsDto = new ApiErrorsDto(ApiErrorCode.RECORD_NOT_FOUND.getValue(), e.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(apiErrorsDto);
    }

    @ExceptionHandler({StorageFailException.class})
    public ResponseEntity<ApiErrorsDto> handle(StorageFailException e, HttpServletRequest request) {
        logException(e, request);

        ApiErrorsDto apiErrorsDto = new ApiErrorsDto(ApiErrorCode.STORAGE_FAIL.getValue(), e.getMessage());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(apiErrorsDto);
    }

    private void logException(Exception e, HttpServletRequest request) {
        log.error("{} {}", request.getMethod(), request.getRequestURI(), e);
    }

}

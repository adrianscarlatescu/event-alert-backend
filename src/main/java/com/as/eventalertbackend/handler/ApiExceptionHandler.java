package com.as.eventalertbackend.handler;

import com.as.eventalertbackend.dto.response.ApiErrorResponseDto;
import com.as.eventalertbackend.dto.response.ApiErrorsResponseDto;
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
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class ApiExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ApiErrorsResponseDto> handle(Exception e, HttpServletRequest request) {
        logException(e, request);

        ApiErrorsResponseDto apiErrorsResponseDto = new ApiErrorsResponseDto("TechnicalException", ApiErrorMessage.TECHNICAL_ERROR.getValue());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(apiErrorsResponseDto);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ApiErrorsResponseDto> handle(MethodArgumentNotValidException e, HttpServletRequest request) {
        logException(e, request);

        List<ApiErrorResponseDto> errors = e.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new ApiErrorResponseDto("FieldValidation", fieldError.getDefaultMessage()))
                .collect(Collectors.toList());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiErrorsResponseDto(errors));
    }

    @ExceptionHandler({InvalidActionException.class})
    public ResponseEntity<ApiErrorsResponseDto> handle(InvalidActionException e, HttpServletRequest request) {
        logException(e, request);

        ApiErrorsResponseDto apiErrorsResponseDto = new ApiErrorsResponseDto("InvalidAction", e.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(apiErrorsResponseDto);
    }

    @ExceptionHandler({RecordNotFoundException.class})
    public ResponseEntity<ApiErrorsResponseDto> handle(RecordNotFoundException e, HttpServletRequest request) {
        logException(e, request);

        ApiErrorsResponseDto apiErrorsResponseDto = new ApiErrorsResponseDto("RecordNotFound", e.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(apiErrorsResponseDto);
    }

    @ExceptionHandler({StorageFailException.class})
    public ResponseEntity<ApiErrorsResponseDto> handle(StorageFailException e, HttpServletRequest request) {
        logException(e, request);

        ApiErrorsResponseDto apiErrorsResponseDto = new ApiErrorsResponseDto("StorageFail", e.getMessage());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(apiErrorsResponseDto);
    }

    private void logException(Exception e, HttpServletRequest request) {
        log.error("{} {}", request.getMethod(), request.getRequestURI(), e);
    }

}

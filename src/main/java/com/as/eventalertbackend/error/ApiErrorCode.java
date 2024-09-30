package com.as.eventalertbackend.error;

import lombok.Getter;

@Getter
public enum ApiErrorCode {

    TECHNICAL_ERROR("technical_error"),
    FIELD_CONSTRAINT("field_constraint"),
    INVALID_ACTION("invalid_action"),
    RECORD_NOT_FOUND("record_not_found"),
    RESOURCE_NOT_FOUND("resource_not_found"),
    STORAGE_FAIL("storage_fail");

    private final String value;

    ApiErrorCode(String value) {
        this.value = value;
    }

}

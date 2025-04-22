package com.as.eventalertbackend.error;

import com.as.eventalertbackend.AppConstants;
import lombok.Getter;

@Getter
public enum ApiErrorMessage {

    TECHNICAL_ERROR("An unexpected error was encountered"),

    ACCOUNT_ALREADY_CREATED("Account already created"),
    PASSWORDS_NOT_MATCH("The passwords do not match"),
    INVALID_REFRESH_TOKEN("Invalid refresh token"),

    SUBSCRIPTION_NOT_FOUND("Subscription not found"),
    ALREADY_SUBSCRIBER("Already subscriber"),

    IMAGE_NOT_FOUND("Image not found"),
    IMAGE_RETRIEVE_FAIL("Could not retrieve the image"),
    IMAGE_STORE_FAIL("Could not store the image"),
    IMAGE_MANDATORY("The image is mandatory"),
    INVALID_IMAGE_PATH("Invalid image path"),

    ROLE_NOT_FOUND("Role not found"),
    USER_NOT_FOUND("User not found"),
    STATUS_NOT_FOUND("Status not found"),
    CATEGORY_NOT_FOUND("Category not found"),
    SEVERITY_NOT_FOUND("Severity not found"),
    TYPE_NOT_FOUND("Type not found"),
    COMMENT_NOT_FOUND("Comment not found"),
    EVENT_NOT_FOUND("Event not found"),

    CATEGORY_ID_EXISTS("A category with this id already exists"),
    TYPE_ID_EXISTS("A type with this id already exists"),
    SEVERITY_ID_EXISTS("A severity with this id already exists"),
    STATUS_ID_EXISTS("A status with this id already exists"),

    CATEGORY_POSITION_EXISTS("A category with this position already exists"),
    TYPE_POSITION_EXISTS("A type with this position already exists"),
    SEVERITY_POSITION_EXISTS("A severity with this position already exists"),
    STATUS_POSITION_EXISTS("A status with this position already exists"),

    CATEGORY_REFERENCED("The category is still referenced by one or more types"),
    TYPE_REFERENCED("The type is still referenced by one or more events"),
    SEVERITY_REFERENCED("The severity is still referenced by one or more events"),
    STATUS_REFERENCED("The status is still referenced by one or more events"),

    DEFAULT_ROLE_MANDATORY("The default role is mandatory"),

    FILTER_MAX_PAGE_SIZE("The page size must be less or equal to " + AppConstants.MAX_PAGE_SIZE),
    FILTER_END_DATE_AFTER_START_DATE("The end date must be after the start date"),
    FILTER_MAX_YEARS_INTERVAL("The time interval must not exceed " + AppConstants.MAX_YEARS_INTERVAL + " years"),

    PROFILE_FULL_NAME_MANDATORY("The profile first name and last name must be set");

    private final String value;

    ApiErrorMessage(String value) {
        this.value = value;
    }

}

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
    INVALID_IMAGE_NAME("Invalid image name"),
    FILE_LIST_FAIL("Could not list server files"),
    IMAGE_RETRIEVE_FAIL("Could not retrieve the image"),
    IMAGE_STORE_FAIL("Could not store the image"),

    ROLE_NOT_FOUND("Role not found"),
    USER_NOT_FOUND("User not found"),
    SEVERITY_NOT_FOUND("Severity not found"),
    TAG_NOT_FOUND("Tag not found"),
    COMMENT_NOT_FOUND("Comment not found"),
    EVENT_NOT_FOUND("Event not found"),

    DEFAULT_ROLE_MANDATORY("The default role is mandatory"),

    FILTER_MAX_PAGE_SIZE("The page size must be less than " + AppConstants.MAX_PAGES),
    FILTER_END_DATE_AFTER_START_DATE("The end date must be after the start date"),
    FILTER_MAX_YEARS_INTERVAL("The years interval must be maximum " + AppConstants.MAX_YEARS_INTERVAL),

    PROFILE_FIRST_NAME_MANDATORY("Profile first name is mandatory"),
    PROFILE_LAST_NAME_MANDATORY("Profile last name is mandatory");

    private final String value;

    ApiErrorMessage(String value) {
        this.value = value;
    }

}

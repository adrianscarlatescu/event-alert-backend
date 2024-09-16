package com.as.eventalertbackend.handler;

import com.as.eventalertbackend.AppConstants;
import lombok.Getter;

@Getter
public enum ApiErrorMessage {

    TECHNICAL_ERROR("An unexpected error was encountered"),

    ACCOUNT_ALREADY_CREATED("The account is already created"),
    PASSWORDS_NOT_MATCH("The passwords are not identical"),
    INVALID_REFRESH_TOKEN("Invalid refresh token"),

    SUBSCRIPTION_NOT_FOUND("Subscription not found"),
    ALREADY_SUBSCRIBER("Already subscriber"),

    IMAGE_RETRIEVE_FAIL("Could not retrieve the image"),
    IMAGE_STORE_FAIL("Could not store the image"),

    ROLE_NOT_FOUND("The role was not found"),
    USER_NOT_FOUND("User not found"),
    DEFAULT_ROLE_MANDATORY("The default role is mandatory"),
    SEVERITY_NOT_FOUND("Severity not found"),
    TAG_NOT_FOUND("Tag not found"),
    COMMENT_NOT_FOUND("Comment not found"),
    EVENT_NOT_FOUND("Comment not found"),

    FILTER_MAX_PAGE_SIZE("The page size must be less than " + AppConstants.MAX_PAGES),
    FILTER_END_DATE_AFTER_START_DATE("The end date must be after the start date"),
    FILTER_MAX_YEARS_INTERVAL("The years interval must be maximum " + AppConstants.MAX_YEARS_INTERVAL),

    USER_FIRST_NAME_MANDATORY("User first name is mandatory"),
    USER_LAST_NAME_MANDATORY("User last name is mandatory");

    private final String value;

    ApiErrorMessage(String value) {
        this.value = value;
    }

}

package com.as.eventalertbackend.handler;

import com.as.eventalertbackend.AppConstants;

public abstract class ApiErrorValidationMessage {

    public static final String MANDATORY_EMAIL = "The email is mandatory";
    public static final String MANDATORY_PASSWORD = "The password is mandatory";
    public static final String MANDATORY_COMMENT = "The comment is mandatory";
    public static final String MANDATORY_EVENT = "The event is mandatory";
    public static final String MANDATORY_USER = "The user is mandatory";
    public static final String MANDATORY_RADIUS = "The radius is mandatory";
    public static final String MANDATORY_START_DATE = "The start date is mandatory";
    public static final String MANDATORY_END_DATE = "The end date is mandatory";
    public static final String MANDATORY_LATITUDE = "The latitude is mandatory";
    public static final String MANDATORY_LONGITUDE = "The longitude is mandatory";
    public static final String MANDATORY_IMAGE_PATH = "The image path is mandatory";
    public static final String MANDATORY_TAG = "The tag is mandatory";
    public static final String MANDATORY_SEVERITY = "The severity is mandatory";
    public static final String MANDATORY_NAME = "The name is mandatory";
    public static final String MANDATORY_COLOR = "The color is mandatory";
    public static final String MANDATORY_DEVICE_TOKEN = "The device token is mandatory";

    public static final String INVALID_EMAIL = "Invalid email";
    public static final String PASSWORD_LENGTH = "The password must have between " +
            AppConstants.MIN_PASSWORD_LENGTH + " and " +
            AppConstants.MAX_PASSWORD_LENGTH + " characters";
    public static final String PASSWORD_CONFIRMATION = "The password must be confirmed";

    public static final String INVALID_DATE_OF_BIRTH = "Invalid date of birth";
    public static final String INVALID_PHONE_FORMAT = "The phone number does not match the expected format";

    public static final String MIN_RADIUS = "The radius must be greater than " + AppConstants.MIN_RADIUS + " km";
    public static final String MAX_RADIUS = "The radius must be less than " + AppConstants.MAX_RADIUS + " km";

    public static final String INVALID_START_DATE = "Invalid start date";
    public static final String INVALID_END_DATE = "Invalid end date";

    public static final String POSITIVE_OR_ZERO_LATITUDE = "The latitude must be positive or 0";
    public static final String POSITIVE_OR_ZERO_LONGITUDE = "The longitude must be positive or 0";

    public static final String MIN_TAG_REQUIRED = "Minimum one tag is required";
    public static final String MIN_SEVERITY_REQUIRED = "Minimum one severity is required";
    public static final String MIN_ROLE_REQUIRED = "Minimum one role is required";

}

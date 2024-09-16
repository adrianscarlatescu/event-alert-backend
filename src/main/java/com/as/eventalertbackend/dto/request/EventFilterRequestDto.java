package com.as.eventalertbackend.dto.request;

import com.as.eventalertbackend.AppConstants;
import com.as.eventalertbackend.handler.ApiErrorValidationMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class EventFilterRequestDto implements Serializable {

    @Min(value = AppConstants.MIN_RADIUS, message = ApiErrorValidationMessage.MIN_RADIUS)
    @Max(value = AppConstants.MAX_RADIUS, message = ApiErrorValidationMessage.MAX_RADIUS)
    @NotNull(message = ApiErrorValidationMessage.MANDATORY_RADIUS)
    public Integer radius;

    @NotNull(message = ApiErrorValidationMessage.MANDATORY_START_DATE)
    @PastOrPresent(message = ApiErrorValidationMessage.INVALID_START_DATE)
    public LocalDate startDate;

    @NotNull(message = ApiErrorValidationMessage.MANDATORY_END_DATE)
    @PastOrPresent(message = ApiErrorValidationMessage.INVALID_END_DATE)
    public LocalDate endDate;

    @NotNull(message = ApiErrorValidationMessage.MANDATORY_LATITUDE)
    @PositiveOrZero(message = ApiErrorValidationMessage.POSITIVE_OR_ZERO_LATITUDE)
    public Double latitude;

    @NotNull(message = ApiErrorValidationMessage.MANDATORY_LONGITUDE)
    @PositiveOrZero(message = ApiErrorValidationMessage.POSITIVE_OR_ZERO_LONGITUDE)
    public Double longitude;

    @NotEmpty(message = ApiErrorValidationMessage.MIN_TAG_REQUIRED)
    public Long[] tagsIds;

    @NotEmpty(message = ApiErrorValidationMessage.MIN_SEVERITY_REQUIRED)
    public Long[] severitiesIds;

}

package com.as.eventalertbackend.dto.request;

import com.as.eventalertbackend.AppConstants;
import com.as.eventalertbackend.handler.ApiErrorValidationMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class SubscriptionRequestDto implements Serializable {

    @NotNull(message = ApiErrorValidationMessage.MANDATORY_LATITUDE)
    @PositiveOrZero(message = ApiErrorValidationMessage.POSITIVE_OR_ZERO_LATITUDE)
    private Double latitude;

    @NotNull(message = ApiErrorValidationMessage.MANDATORY_LONGITUDE)
    @PositiveOrZero(message = ApiErrorValidationMessage.POSITIVE_OR_ZERO_LONGITUDE)
    private Double longitude;

    @Min(value = AppConstants.MIN_RADIUS, message = ApiErrorValidationMessage.MIN_RADIUS)
    @Max(value = AppConstants.MAX_RADIUS, message = ApiErrorValidationMessage.MAX_RADIUS)
    @NotNull(message = ApiErrorValidationMessage.MANDATORY_RADIUS)
    private Integer radius;

    @NotBlank(message = ApiErrorValidationMessage.MANDATORY_DEVICE_TOKEN)
    private String deviceToken;

}

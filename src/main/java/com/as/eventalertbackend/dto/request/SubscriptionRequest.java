package com.as.eventalertbackend.dto.request;

import com.as.eventalertbackend.AppConstants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class SubscriptionRequest implements Serializable {

    @NotNull(message = "The user is mandatory")
    private Long userId;

    @NotNull(message = "The latitude is mandatory")
    @PositiveOrZero(message = "The latitude must be positive or 0")
    private Double latitude;

    @NotNull(message = "The longitude is mandatory")
    @PositiveOrZero(message = "The longitude must be positive or 0")
    private Double longitude;

    @Min(value = AppConstants.MIN_RADIUS, message = "The radius must be greater or equal to " + AppConstants.MIN_RADIUS + " km")
    @Max(value = AppConstants.MAX_RADIUS, message = "The radius must be less or equal to " + AppConstants.MAX_RADIUS + " km")
    @NotNull(message = "The radius is mandatory")
    private Integer radius;

    @NotBlank(message = "The device identifier is mandatory")
    private String deviceId;

    @NotBlank(message = "The Firebase token is mandatory")
    private String firebaseToken;

}

package com.as.eventalertbackend.dto.subscription;

import com.as.eventalertbackend.AppConstants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class SubscriptionCreateDTO implements Serializable {

    @NotNull(message = "The user is mandatory")
    private Long userId;

    @NotNull(message = "The latitude is mandatory")
    @PositiveOrZero(message = "The latitude must be greater or equal to 0")
    private Double latitude;

    @NotNull(message = "The longitude is mandatory")
    @PositiveOrZero(message = "The longitude must be greater or equal to 0")
    private Double longitude;

    @NotNull(message = "The radius is mandatory")
    @Min(value = AppConstants.MIN_RADIUS, message = "The radius must be greater or equal to " + AppConstants.MIN_RADIUS + " km")
    @Max(value = AppConstants.MAX_RADIUS, message = "The radius must be less or equal to " + AppConstants.MAX_RADIUS + " km")
    private Integer radius;

    @NotBlank(message = "The device identifier is mandatory")
    private String deviceId;

    @NotBlank(message = "The Firebase token is mandatory")
    private String firebaseToken;

}

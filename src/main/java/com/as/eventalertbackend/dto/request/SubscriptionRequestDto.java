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
public class SubscriptionRequestDto implements Serializable {

    @NotNull(message = "The latitude is mandatory")
    @PositiveOrZero(message = "The latitude must be positive or 0")
    private Double latitude;

    @NotNull(message = "The longitude is mandatory")
    @PositiveOrZero(message = "The longitude must be positive or 0")
    private Double longitude;

    @Min(value = AppConstants.MIN_RADIUS, message = "The radius must be greater than " + AppConstants.MIN_RADIUS + " km")
    @Max(value = AppConstants.MAX_RADIUS, message = "The radius must be less than " + AppConstants.MAX_RADIUS + " km")
    @NotNull(message = "The radius is mandatory")
    @Positive(message = "The radius must be positive")
    private Integer radius;

    @NotBlank
    private String deviceToken;

}

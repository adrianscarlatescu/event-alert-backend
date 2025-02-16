package com.as.eventalertbackend.dto.subscription;

import com.as.eventalertbackend.AppConstants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class SubscriptionUpdateDTO implements Serializable {

    @PositiveOrZero(message = "The latitude must be greater or equal to 0")
    private Double latitude;

    @PositiveOrZero(message = "The longitude must be greater or equal to 0")
    private Double longitude;

    @Min(value = AppConstants.MIN_RADIUS, message = "The radius must be greater or equal to " + AppConstants.MIN_RADIUS + " km")
    @Max(value = AppConstants.MAX_RADIUS, message = "The radius must be less or equal to " + AppConstants.MAX_RADIUS + " km")
    private Integer radius;

    private String firebaseToken;

}

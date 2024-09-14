package com.as.eventalertbackend.controller.request;

import com.as.eventalertbackend.AppConstants;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class SubscriptionRequestDto implements Serializable {

    @NotNull(message = "The latitude is mandatory")
    private Double latitude;

    @NotNull(message = "The longitude is mandatory")
    private Double longitude;

    @Min(value = AppConstants.MIN_RADIUS, message = "The radius must be greater than " + AppConstants.MIN_RADIUS + " km")
    @Max(value = AppConstants.MAX_RADIUS, message = "The radius must be less than " + AppConstants.MAX_RADIUS + " km")
    @NotNull(message = "The radius is mandatory")
    private Integer radius;

    @NotBlank
    private String deviceToken;

}

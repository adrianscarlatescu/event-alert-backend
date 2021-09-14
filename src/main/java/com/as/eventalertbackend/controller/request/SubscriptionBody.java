package com.as.eventalertbackend.controller.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class SubscriptionBody {

    @NotNull(message = "The latitude is mandatory")
    private Double latitude;
    @NotNull(message = "The longitude is mandatory")
    private Double longitude;
    @Min(value = RequestConstants.MIN_RADIUS, message = "The radius must be greater than " + RequestConstants.MIN_RADIUS + " km")
    @Max(value = RequestConstants.MAX_RADIUS, message = "The radius must be less than " + RequestConstants.MAX_RADIUS + " km")
    private Integer radius;

}

package com.as.eventalertbackend.controller.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
public class EventFilterBody {

    @Min(value = RequestConstants.MIN_RADIUS, message = "The radius must be greater than " + RequestConstants.MIN_RADIUS + " km")
    @Max(value = RequestConstants.MAX_RADIUS, message = "The radius must be less than " + RequestConstants.MAX_RADIUS + " km")
    public int radius;
    @NotNull(message = "The start date is mandatory")
    public LocalDate startDate;
    @NotNull(message = "The end date is mandatory")
    public LocalDate endDate;
    @NotNull(message = "The latitude is mandatory")
    public Double latitude;
    @NotNull(message = "The longitude is mandatory")
    public Double longitude;
    @NotNull(message = "Minimum one tag is required")
    public long[] tagsIds;
    @NotNull(message = "Minimum one severity is required")
    public long[] severitiesIds;

}

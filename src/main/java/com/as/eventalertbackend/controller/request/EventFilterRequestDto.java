package com.as.eventalertbackend.controller.request;

import com.as.eventalertbackend.AppConstants;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class EventFilterRequestDto implements Serializable {

    @Min(value = AppConstants.MIN_RADIUS, message = "The radius must be greater than " + AppConstants.MIN_RADIUS + " km")
    @Max(value = AppConstants.MAX_RADIUS, message = "The radius must be less than " + AppConstants.MAX_RADIUS + " km")
    @NotNull(message = "The radius is mandatory")
    public int radius;

    @NotNull(message = "The start date is mandatory")
    public LocalDate startDate;

    @NotNull(message = "The end date is mandatory")
    public LocalDate endDate;

    @NotNull(message = "The latitude is mandatory")
    public double latitude;

    @NotNull(message = "The longitude is mandatory")
    public double longitude;

    @NotEmpty(message = "Minimum one tag is required")
    public long[] tagsIds;

    @NotEmpty(message = "Minimum one severity is required")
    public long[] severitiesIds;

}

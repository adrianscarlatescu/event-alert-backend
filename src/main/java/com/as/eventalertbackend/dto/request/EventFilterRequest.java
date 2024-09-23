package com.as.eventalertbackend.dto.request;

import com.as.eventalertbackend.AppConstants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class EventFilterRequest implements Serializable {

    @Min(value = AppConstants.MIN_RADIUS, message = "The radius must be greater than " + AppConstants.MIN_RADIUS + " km")
    @Max(value = AppConstants.MAX_RADIUS, message = "The radius must be less than " + AppConstants.MAX_RADIUS + " km")
    @NotNull(message = "The radius is mandatory")
    public Integer radius;

    @NotNull(message = "The start date is mandatory")
    public LocalDate startDate;

    @NotNull(message = "The end date is mandatory")
    public LocalDate endDate;

    @NotNull(message = "The latitude is mandatory")
    @PositiveOrZero(message = "The latitude must be positive or 0")
    public Double latitude;

    @NotNull(message = "The longitude is mandatory")
    @PositiveOrZero(message = "The longitude must be positive or 0")
    public Double longitude;

    @NotEmpty(message = "Minimum one tag is required")
    public Set<Long> tagsIds;

    @NotEmpty(message = "Minimum one severity is required")
    public Set<Long> severitiesIds;

}
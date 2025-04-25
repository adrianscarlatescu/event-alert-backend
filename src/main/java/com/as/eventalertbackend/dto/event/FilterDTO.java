package com.as.eventalertbackend.dto.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import static com.as.eventalertbackend.AppConstants.MAX_RADIUS;
import static com.as.eventalertbackend.AppConstants.MIN_RADIUS;

@Getter
@Setter
@NoArgsConstructor
public class FilterDTO implements Serializable {

    @NotNull(message = "The radius is mandatory")
    @Min(value = MIN_RADIUS, message = "The radius must be greater or equal to " + MIN_RADIUS + " km")
    @Max(value = MAX_RADIUS, message = "The radius must be less or equal to " + MAX_RADIUS + " km")
    private Integer radius;

    @NotNull(message = "The start date is mandatory")
    @PastOrPresent(message = "The start date must be in the past or present")
    private LocalDate startDate;

    @NotNull(message = "The end date is mandatory")
    @PastOrPresent(message = "The end date must be in the past or present")
    private LocalDate endDate;

    @NotNull(message = "The latitude is mandatory")
    @PositiveOrZero(message = "The latitude must be greater or equal to 0")
    private Double latitude;

    @NotNull(message = "The longitude is mandatory")
    @PositiveOrZero(message = "The longitude must be greater or equal to 0")
    private Double longitude;

    @NotEmpty(message = "At least one type is required")
    private Set<String> typeIds;

    @NotEmpty(message = "At least one severity is required")
    private Set<String> severityIds;

    @NotEmpty(message = "At least one status is required")
    private Set<String> statusIds;

}

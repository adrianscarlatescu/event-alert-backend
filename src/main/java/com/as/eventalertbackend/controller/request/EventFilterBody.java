package com.as.eventalertbackend.controller.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
public class EventFilterBody {

    @Min(value = 0, message = "The radius must be greater than 0")
    public int radius;
    @NotNull(message = "The start date is mandatory")
    public LocalDate startDate;
    @NotNull(message = "The end date is mandatory")
    public LocalDate endDate;
    public double latitude;
    public double longitude;
    @NotNull(message = "Minimum one tag is required")
    public long[] tagsIds;
    @NotNull(message = "Minimum one severity is required")
    public long[] severitiesIds;

}

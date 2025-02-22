package com.as.eventalertbackend.dto.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

import static com.as.eventalertbackend.AppConstants.*;

@Getter
@Setter
@NoArgsConstructor
public class EventUpdateDTO implements Serializable {

    @DecimalMin(value = MIN_IMPACT_RADIUS, message = "The impact radius must be greater or equal to " + MIN_IMPACT_RADIUS + " km")
    @DecimalMax(value = MAX_IMPACT_RADIUS, message = "The impact radius must be less or equal to " + MAX_IMPACT_RADIUS + " km")
    public BigDecimal impactRadius;

    @NotNull(message = "The severity is mandatory")
    private String severityId;

    @NotNull(message = "The type is mandatory")
    private String typeId;

    @NotNull(message = "The status is mandatory")
    private String statusId;

    @NotBlank(message = "The image path is mandatory")
    private String imagePath;

    @Size(max = LENGTH_1000, message = "The description must not exceed " + LENGTH_1000 + " characters")
    private String description;

}

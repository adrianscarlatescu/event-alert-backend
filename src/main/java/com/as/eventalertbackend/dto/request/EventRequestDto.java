package com.as.eventalertbackend.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class EventRequestDto implements Serializable {

    @NotNull(message = "The latitude is mandatory")
    @PositiveOrZero(message = "The latitude must be positive or 0")
    private Double latitude;

    @NotNull(message = "The longitude is mandatory")
    @PositiveOrZero(message = "The longitude must be positive or 0")
    private Double longitude;

    @NotBlank(message = "The image is mandatory")
    private String imagePath;

    @NotNull(message = "The severity is mandatory")
    private Long severityId;

    @NotNull(message = "The tag is mandatory")
    private Long tagId;

    @NotNull(message = "The user is mandatory")
    private Long userId;

    private String description;

}

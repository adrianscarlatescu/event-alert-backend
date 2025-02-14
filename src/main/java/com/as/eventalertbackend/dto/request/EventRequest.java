package com.as.eventalertbackend.dto.request;

import com.as.eventalertbackend.AppConstants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class EventRequest implements Serializable {

    @NotNull(message = "The latitude is mandatory")
    @PositiveOrZero(message = "The latitude must be greater or equal to 0")
    private Double latitude;

    @NotNull(message = "The longitude is mandatory")
    @PositiveOrZero(message = "The longitude must be greater or equal to 0")
    private Double longitude;

    @NotBlank(message = "The image path is mandatory")
    private String imagePath;

    @NotNull(message = "The severity is mandatory")
    private Long severityId;

    @NotNull(message = "The tag is mandatory")
    private Long tagId;

    @NotNull(message = "The user is mandatory")
    private Long userId;

    @Size(max = AppConstants.MAX_DESCRIPTION_LENGTH,
            message = "The description must not exceed " + AppConstants.MAX_DESCRIPTION_LENGTH + " characters")
    private String description;

}

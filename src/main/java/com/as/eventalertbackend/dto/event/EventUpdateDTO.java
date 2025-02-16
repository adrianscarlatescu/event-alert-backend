package com.as.eventalertbackend.dto.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.io.Serializable;

import static com.as.eventalertbackend.AppConstants.MAX_DESCRIPTION_LENGTH;

@Getter
@Setter
@NoArgsConstructor
public class EventUpdateDTO implements Serializable {

    @PositiveOrZero(message = "The latitude must be greater or equal to 0")
    private Double latitude;

    @PositiveOrZero(message = "The longitude must be greater or equal to 0")
    private Double longitude;

    private String imagePath;

    private Long severityId;

    private Long typeId;

    private Long userId;

    @Size(max = MAX_DESCRIPTION_LENGTH, message = "The description must not exceed " + MAX_DESCRIPTION_LENGTH + " characters")
    private String description;

}

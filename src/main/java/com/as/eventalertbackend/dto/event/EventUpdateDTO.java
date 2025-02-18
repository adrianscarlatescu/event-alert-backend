package com.as.eventalertbackend.dto.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

import static com.as.eventalertbackend.AppConstants.MAX_LENGTH_1000;

@Getter
@Setter
@NoArgsConstructor
public class EventUpdateDTO implements Serializable {

    @NotBlank(message = "The image path is mandatory")
    private String imagePath;

    @NotNull(message = "The severity is mandatory")
    private Long severityId;

    @NotNull(message = "The type is mandatory")
    private Long typeId;

    @Size(max = MAX_LENGTH_1000, message = "The description must not exceed " + MAX_LENGTH_1000 + " characters")
    private String description;

}

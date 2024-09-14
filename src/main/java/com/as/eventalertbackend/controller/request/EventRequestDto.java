package com.as.eventalertbackend.controller.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class EventRequestDto implements Serializable {

    @NotNull(message = "The latitude is mandatory")
    private Double latitude;

    @NotNull(message = "The longitude is mandatory")
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

package com.as.eventalertbackend.controller.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class EventBody {

    @NotNull(message = "The latitude is mandatory")
    private Double latitude;
    @NotNull(message = "The longitude is mandatory")
    private Double longitude;
    @NotEmpty(message = "The image is mandatory")
    private String imagePath;
    @NotNull(message = "The severity is mandatory")
    private Long severityId;
    @NotNull(message = "The tag is mandatory")
    private Long tagId;
    @NotNull(message = "The user is mandatory")
    private Long userId;
    private String description;

}

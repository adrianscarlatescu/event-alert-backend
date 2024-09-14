package com.as.eventalertbackend.controller.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class EventTagRequestDto implements Serializable {

    @NotBlank(message = "The name is mandatory")
    private String name;

    @NotBlank(message = "The image path is mandatory")
    private String imagePath;

}

package com.as.eventalertbackend.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class EventTagRequest implements Serializable {

    @NotBlank(message = "The name is mandatory")
    private String name;

    @NotBlank(message = "The image path is mandatory")
    private String imagePath;

}

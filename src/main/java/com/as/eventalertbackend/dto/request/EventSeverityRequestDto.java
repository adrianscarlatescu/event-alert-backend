package com.as.eventalertbackend.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class EventSeverityRequestDto implements Serializable {

    @NotBlank(message = "The name is mandatory")
    private String name;

    @NotNull(message = "The color is mandatory")
    private Integer color;

}

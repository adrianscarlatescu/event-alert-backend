package com.as.eventalertbackend.dto.severity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;

import static com.as.eventalertbackend.AppConstants.LENGTH_50;
import static com.as.eventalertbackend.AppConstants.LENGTH_7;

@Getter
@Setter
@NoArgsConstructor
public class SeverityUpdateDTO implements Serializable {

    @NotBlank(message = "The label is mandatory")
    @Size(max = LENGTH_50, message = "The label must not exceed " + LENGTH_50 + " characters")
    private String label;

    @NotNull(message = "The color is mandatory")
    @Size(min = LENGTH_7, max = LENGTH_7, message = "The color must have exactly " + LENGTH_7 + " characters")
    private String color;

    @NotNull(message = "The position is mandatory")
    @Positive(message = "The position must be greater than 0")
    private Integer position;

}

package com.as.eventalertbackend.dto.severity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;

import static com.as.eventalertbackend.AppConstants.FIXED_COLOR_LENGTH;
import static com.as.eventalertbackend.AppConstants.MAX_LENGTH_50;

@Getter
@Setter
@NoArgsConstructor
public class SeverityUpdateDTO implements Serializable {

    @NotBlank(message = "The label is mandatory")
    @Size(max = MAX_LENGTH_50, message = "The label must not exceed " + MAX_LENGTH_50 + " characters")
    private String label;

    @NotNull(message = "The color is mandatory")
    @Size(min = FIXED_COLOR_LENGTH, max = FIXED_COLOR_LENGTH, message = "The color must have exactly " + FIXED_COLOR_LENGTH + " characters")
    private String color;

    @NotNull(message = "The position is mandatory")
    @Positive(message = "The position must be greater than 0")
    private Integer position;

}

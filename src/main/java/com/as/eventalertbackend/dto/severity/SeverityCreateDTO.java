package com.as.eventalertbackend.dto.severity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

import static com.as.eventalertbackend.AppConstants.MAX_LENGTH_50;

@Getter
@Setter
@NoArgsConstructor
public class SeverityCreateDTO implements Serializable {

    @NotBlank(message = "The code is mandatory")
    @Size(max = MAX_LENGTH_50, message = "The code must not exceed " + MAX_LENGTH_50 + " characters")
    private String code;

    @NotBlank(message = "The label is mandatory")
    @Size(max = MAX_LENGTH_50, message = "The label must not exceed " + MAX_LENGTH_50 + " characters")
    private String label;

    @NotNull(message = "The color is mandatory")
    private Integer color;

}

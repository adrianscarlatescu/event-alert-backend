package com.as.eventalertbackend.dto.request;

import com.as.eventalertbackend.AppConstants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class EventSeverityRequest implements Serializable {

    @NotBlank(message = "The name is mandatory")
    @Size(max = AppConstants.MAX_SEVERITY_NAME_LENGTH,
            message = "The name must have at most " + AppConstants.MAX_SEVERITY_NAME_LENGTH + " characters")
    private String name;

    @NotNull(message = "The color is mandatory")
    private Integer color;

}

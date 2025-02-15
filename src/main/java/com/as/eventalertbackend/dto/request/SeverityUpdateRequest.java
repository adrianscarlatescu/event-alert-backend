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
public class SeverityUpdateRequest implements Serializable {

    @NotNull(message = "The code is mandatory")
    private String code;

    @NotBlank(message = "The label is mandatory")
    @Size(max = AppConstants.MAX_SEVERITY_LABEL_LENGTH,
            message = "The label must not exceed " + AppConstants.MAX_SEVERITY_LABEL_LENGTH + " characters")
    private String label;

    @NotNull(message = "The color is mandatory")
    private Integer color;

}

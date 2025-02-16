package com.as.eventalertbackend.dto.severity;

import com.as.eventalertbackend.AppConstants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class SeverityUpdateDTO implements Serializable {

    @Size(max = AppConstants.MAX_DEFAULT_LENGTH,
            message = "The code must not exceed " + AppConstants.MAX_DEFAULT_LENGTH + " characters")
    private String code;

    @Size(max = AppConstants.MAX_DEFAULT_LENGTH,
            message = "The label must not exceed " + AppConstants.MAX_DEFAULT_LENGTH + " characters")
    private String label;

    private Integer color;

}

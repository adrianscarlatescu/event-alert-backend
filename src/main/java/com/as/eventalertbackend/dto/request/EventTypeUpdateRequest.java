package com.as.eventalertbackend.dto.request;

import com.as.eventalertbackend.AppConstants;
import com.as.eventalertbackend.model.EventTypeCode;
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
public class EventTypeUpdateRequest implements Serializable {

    @NotNull(message = "The code is mandatory")
    private EventTypeCode code;

    @NotBlank(message = "The label is mandatory")
    @Size(max = AppConstants.MAX_TYPE_LABEL_LENGTH,
            message = "The label must not exceed " + AppConstants.MAX_TYPE_LABEL_LENGTH + " characters")
    private String label;

    @NotBlank(message = "The image path is mandatory")
    private String imagePath;

}

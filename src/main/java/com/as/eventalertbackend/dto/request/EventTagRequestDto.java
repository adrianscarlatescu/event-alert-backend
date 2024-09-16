package com.as.eventalertbackend.dto.request;

import com.as.eventalertbackend.handler.ApiErrorValidationMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class EventTagRequestDto implements Serializable {

    @NotBlank(message = ApiErrorValidationMessage.MANDATORY_NAME)
    private String name;

    @NotBlank(message = ApiErrorValidationMessage.MANDATORY_IMAGE_PATH)
    private String imagePath;

}

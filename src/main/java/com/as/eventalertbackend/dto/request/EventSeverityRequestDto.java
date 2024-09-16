package com.as.eventalertbackend.dto.request;

import com.as.eventalertbackend.handler.ApiErrorValidationMessage;
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

    @NotBlank(message = ApiErrorValidationMessage.MANDATORY_NAME)
    private String name;

    @NotNull(message = ApiErrorValidationMessage.MANDATORY_COLOR)
    private Integer color;

}

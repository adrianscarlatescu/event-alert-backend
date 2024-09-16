package com.as.eventalertbackend.dto.request;

import com.as.eventalertbackend.handler.ApiErrorValidationMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class EventRequestDto implements Serializable {

    @NotNull(message = ApiErrorValidationMessage.MANDATORY_LATITUDE)
    @PositiveOrZero(message = ApiErrorValidationMessage.POSITIVE_OR_ZERO_LATITUDE)
    private Double latitude;

    @NotNull(message = ApiErrorValidationMessage.MANDATORY_LONGITUDE)
    @PositiveOrZero(message = ApiErrorValidationMessage.POSITIVE_OR_ZERO_LONGITUDE)
    private Double longitude;

    @NotBlank(message = ApiErrorValidationMessage.MANDATORY_IMAGE_PATH)
    private String imagePath;

    @NotNull(message = ApiErrorValidationMessage.MANDATORY_SEVERITY)
    private Long severityId;

    @NotNull(message = ApiErrorValidationMessage.MANDATORY_TAG)
    private Long tagId;

    @NotNull(message = ApiErrorValidationMessage.MANDATORY_USER)
    private Long userId;

    private String description;

}

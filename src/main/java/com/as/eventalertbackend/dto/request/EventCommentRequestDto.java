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
public class EventCommentRequestDto implements Serializable {

    @NotBlank(message = ApiErrorValidationMessage.MANDATORY_COMMENT)
    private String comment;

    @NotNull(message = ApiErrorValidationMessage.MANDATORY_EVENT)
    private Long eventId;

    @NotNull(message = ApiErrorValidationMessage.MANDATORY_USER)
    private Long userId;

}

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
public class EventCommentRequest implements Serializable {

    @Size(max = AppConstants.MAX_COMMENT_LENGTH,
            message = "The comment must not exceed " + AppConstants.MAX_COMMENT_LENGTH + " characters")
    @NotBlank(message = "The comment is mandatory")
    private String comment;

    @NotNull(message = "The event is mandatory")
    private Long eventId;

    @NotNull(message = "The user is mandatory")
    private Long userId;

}

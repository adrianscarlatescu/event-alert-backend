package com.as.eventalertbackend.dto.comment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

import static com.as.eventalertbackend.AppConstants.LENGTH_1000;

@Getter
@Setter
@NoArgsConstructor
public class CommentCreateDTO implements Serializable {

    @NotBlank(message = "The comment is mandatory")
    @Size(max = LENGTH_1000, message = "The comment must not exceed " + LENGTH_1000 + " characters")
    private String comment;

    @NotNull(message = "The event is mandatory")
    private Long eventId;

    @NotNull(message = "The user is mandatory")
    private Long userId;

}

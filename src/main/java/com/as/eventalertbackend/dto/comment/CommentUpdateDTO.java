package com.as.eventalertbackend.dto.comment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

import static com.as.eventalertbackend.AppConstants.MAX_LENGTH_1000;

@Getter
@Setter
@NoArgsConstructor
public class CommentUpdateDTO implements Serializable {

    @NotBlank(message = "The comment is mandatory")
    @Size(max = MAX_LENGTH_1000, message = "The comment must not exceed " + MAX_LENGTH_1000 + " characters")
    private String comment;

}

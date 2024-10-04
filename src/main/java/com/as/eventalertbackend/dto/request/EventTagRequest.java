package com.as.eventalertbackend.dto.request;

import com.as.eventalertbackend.AppConstants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class EventTagRequest implements Serializable {

    @NotBlank(message = "The name is mandatory")
    @Size(max = AppConstants.MAX_TAG_NAME_LENGTH,
            message = "The name must not exceed " + AppConstants.MAX_TAG_NAME_LENGTH + " characters")
    private String name;

    @NotBlank(message = "The image path is mandatory")
    private String imagePath;

}

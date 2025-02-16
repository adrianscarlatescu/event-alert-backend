package com.as.eventalertbackend.dto.category;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.io.Serializable;

import static com.as.eventalertbackend.AppConstants.MAX_DEFAULT_LENGTH;

@Getter
@Setter
@NoArgsConstructor
public class CategoryUpdateDTO implements Serializable {

    @Size(max = MAX_DEFAULT_LENGTH, message = "The code must not exceed " + MAX_DEFAULT_LENGTH + " characters")
    private String code;

    @Size(max = MAX_DEFAULT_LENGTH, message = "The label must not exceed " + MAX_DEFAULT_LENGTH + " characters")
    private String label;

    private String imagePath;

}

package com.as.eventalertbackend.dto.category;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;

import static com.as.eventalertbackend.AppConstants.MAX_LENGTH_50;

@Getter
@Setter
@NoArgsConstructor
public class CategoryUpdateDTO implements Serializable {

    @NotBlank(message = "The label is mandatory")
    @Size(max = MAX_LENGTH_50, message = "The label must not exceed " + MAX_LENGTH_50 + " characters")
    private String label;

    @NotBlank(message = "The image path is mandatory")
    private String imagePath;

    @NotNull(message = "The position is mandatory")
    @Positive(message = "The position must be greater than 0")
    private Integer position;

}

package com.as.eventalertbackend.dto.type;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

import static com.as.eventalertbackend.AppConstants.LENGTH_50;

@Getter
@Setter
@NoArgsConstructor
public class TypeUpdateDTO implements Serializable {

    @NotNull(message = "The category is mandatory")
    private Long categoryId;

    @NotBlank(message = "The label is mandatory")
    @Size(max = LENGTH_50, message = "The label must not exceed " + LENGTH_50 + " characters")
    private String label;

    @NotBlank(message = "The image path is mandatory")
    private String imagePath;

}

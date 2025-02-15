package com.as.eventalertbackend.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class TypeResponse implements Serializable {

    private Long id;
    private String code;
    private String label;
    private String imagePath;
    private CategoryResponse category;

}

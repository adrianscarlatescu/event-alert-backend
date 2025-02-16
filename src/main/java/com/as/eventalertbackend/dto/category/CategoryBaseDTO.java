package com.as.eventalertbackend.dto.category;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class CategoryBaseDTO implements Serializable {

    private Long id;
    private String code;
    private String label;
    private String imagePath;

}

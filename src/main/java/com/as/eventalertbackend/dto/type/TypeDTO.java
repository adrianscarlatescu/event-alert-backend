package com.as.eventalertbackend.dto.type;

import com.as.eventalertbackend.dto.category.CategoryBaseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class TypeDTO implements Serializable {

    private Long id;
    private String code;
    private String label;
    private String imagePath;
    private CategoryBaseDTO category;

}

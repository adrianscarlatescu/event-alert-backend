package com.as.eventalertbackend.dto.category;

import com.as.eventalertbackend.dto.type.TypeBaseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDTO implements Serializable {

    private Long id;
    private String code;
    private String label;
    private String imagePath;
    private List<TypeBaseDTO> types;

}

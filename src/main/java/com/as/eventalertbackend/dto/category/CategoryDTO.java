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

    private String id;
    private String label;
    private String imagePath;
    private Integer position;
    private List<TypeBaseDTO> types;

}

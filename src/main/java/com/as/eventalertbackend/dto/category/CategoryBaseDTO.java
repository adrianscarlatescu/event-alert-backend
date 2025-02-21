package com.as.eventalertbackend.dto.category;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class CategoryBaseDTO implements Serializable {

    private String id;
    private String label;
    private String imagePath;
    private Integer position;

}

package com.as.eventalertbackend.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CategoryResponse implements Serializable {

    private Long id;
    private String label;
    private String imagePath;
    private List<TypeBaseResponse> types;

}

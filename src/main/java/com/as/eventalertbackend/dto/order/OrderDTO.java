package com.as.eventalertbackend.dto.order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class OrderDTO implements Serializable {

    private String id;
    private String label;
    private String imagePath;
    private Integer position;

}

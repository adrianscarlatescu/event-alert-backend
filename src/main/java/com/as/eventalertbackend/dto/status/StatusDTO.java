package com.as.eventalertbackend.dto.status;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class StatusDTO implements Serializable {

    private String id;
    private String label;
    private String color;
    private String description;
    private Integer position;

}

package com.as.eventalertbackend.dto.severity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class SeverityDTO implements Serializable {

    private String id;
    private String label;
    private String color;
    private Integer position;

}

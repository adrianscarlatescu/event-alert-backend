package com.as.eventalertbackend.dto.severity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class SeverityDTO implements Serializable {

    private Long id;
    private String code;
    private String label;
    private Integer color;

}

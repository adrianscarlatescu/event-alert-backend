package com.as.eventalertbackend.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class EventSeverityResponse implements Serializable {

    private Long id;
    private String label;
    private Integer color;

}

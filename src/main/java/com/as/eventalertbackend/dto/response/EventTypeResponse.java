package com.as.eventalertbackend.dto.response;

import com.as.eventalertbackend.model.EventTypeCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class EventTypeResponse implements Serializable {

    private Long id;
    private EventTypeCode code;
    private String label;
    private String imagePath;
    private EventTypeCategoryResponse category;

}

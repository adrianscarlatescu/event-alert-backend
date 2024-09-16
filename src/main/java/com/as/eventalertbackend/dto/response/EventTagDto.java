package com.as.eventalertbackend.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class EventTagDto implements Serializable {

    private Long id;
    private String name;
    private String imagePath;

}

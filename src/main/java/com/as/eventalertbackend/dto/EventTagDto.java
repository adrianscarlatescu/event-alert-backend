package com.as.eventalertbackend.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class EventTagDto implements Serializable {

    private Long id;
    private String name;
    private String imagePath;

}

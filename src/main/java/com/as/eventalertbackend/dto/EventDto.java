package com.as.eventalertbackend.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class EventDto implements Serializable {

    private Long id;
    private LocalDateTime dateTime;
    private Double latitude;
    private Double longitude;
    private String imagePath;
    private String description;
    private Set<EventCommentDto> eventComments;
    private EventSeverityDto severity;
    private EventTagDto tag;
    private UserDto user;
    private Double distance;

}

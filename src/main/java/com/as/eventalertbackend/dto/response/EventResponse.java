package com.as.eventalertbackend.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class EventResponse implements Serializable {

    private Long id;
    private LocalDateTime createdAt;
    private Double latitude;
    private Double longitude;
    private String imagePath;
    private String description;
    private Set<EventCommentResponse> eventComments;
    private EventSeverityResponse severity;
    private EventTypeResponse type;
    private UserBaseResponse user;
    private Double distance;

}

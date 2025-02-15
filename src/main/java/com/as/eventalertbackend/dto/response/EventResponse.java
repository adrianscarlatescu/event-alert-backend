package com.as.eventalertbackend.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
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
    private List<CommentResponse> comments;
    private SeverityResponse severity;
    private TypeResponse type;
    private UserBaseResponse user;
    private Double distance;

}

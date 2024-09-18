package com.as.eventalertbackend.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class EventCommentDto implements Serializable {

    private Long id;
    private LocalDateTime dateTime;
    private String comment;
    private UserBaseDto user;

}

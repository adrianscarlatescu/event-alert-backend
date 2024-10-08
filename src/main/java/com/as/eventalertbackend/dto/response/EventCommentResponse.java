package com.as.eventalertbackend.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class EventCommentResponse implements Serializable {

    private Long id;
    private LocalDateTime dateTime;
    private String comment;
    private UserBaseResponse user;

}

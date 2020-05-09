package com.as.eventalertbackend.controller.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class EventCommentBody {

    @NotNull(message = "The comment is mandatory")
    private String comment;
    @NotNull(message = "The event is mandatory")
    private Long eventId;
    @NotNull(message = "The user is mandatory")
    private Long userId;

}

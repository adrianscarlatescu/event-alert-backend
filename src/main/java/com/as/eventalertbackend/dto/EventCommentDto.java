package com.as.eventalertbackend.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class EventCommentDto implements Serializable {

    private Long id;
    private LocalDateTime dateTime;
    private String comment;
    private UserDto user;

}

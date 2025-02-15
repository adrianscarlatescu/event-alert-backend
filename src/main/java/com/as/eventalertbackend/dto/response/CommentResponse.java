package com.as.eventalertbackend.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CommentResponse implements Serializable {

    private Long id;
    private LocalDateTime createdAt;
    private String comment;
    private UserBaseResponse user;

}

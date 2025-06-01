package com.as.eventalertbackend.dto.comment;

import com.as.eventalertbackend.dto.user.UserBaseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CommentDTO implements Serializable {

    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String comment;
    private UserBaseDTO user;

}

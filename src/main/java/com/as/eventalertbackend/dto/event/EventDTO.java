package com.as.eventalertbackend.dto.event;

import com.as.eventalertbackend.dto.comment.CommentDTO;
import com.as.eventalertbackend.dto.severity.SeverityDTO;
import com.as.eventalertbackend.dto.type.TypeDTO;
import com.as.eventalertbackend.dto.user.UserBaseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class EventDTO implements Serializable {

    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Double latitude;
    private Double longitude;
    private String imagePath;
    private String description;
    private List<CommentDTO> comments;
    private SeverityDTO severity;
    private TypeDTO type;
    private UserBaseDTO user;
    private Double distance;

}

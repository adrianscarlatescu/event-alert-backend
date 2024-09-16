package com.as.eventalertbackend.jpa.entity;

import com.as.eventalertbackend.dto.response.EventCommentResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
@Getter
@Setter
@NoArgsConstructor
public class EventComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime dateTime;

    private String comment;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public EventCommentResponseDto toDto() {
        EventCommentResponseDto dto = new EventCommentResponseDto();
        dto.setId(getId());
        dto.setDateTime(getDateTime());
        dto.setComment(getComment());
        dto.setUser(getUser() == null ? null : getUser().toDto());
        return dto;
    }

}

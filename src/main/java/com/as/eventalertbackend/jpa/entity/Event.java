package com.as.eventalertbackend.jpa.entity;

import com.as.eventalertbackend.dto.response.EventResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime dateTime;

    private Double latitude;
    private Double longitude;
    private String imagePath;
    private String description;

    @OneToMany(mappedBy = "event")
    private Set<EventComment> eventComments;

    @ManyToOne
    @JoinColumn(name = "severity_id", nullable = false)
    private EventSeverity severity;

    @ManyToOne
    @JoinColumn(name = "tag_id", nullable = false)
    private EventTag tag;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Transient
    private double distance;

    @Override
    public String toString() {
        return "{id: " + id + ", date_time: " + dateTime.toString() +
                ", latitude: " + latitude + ", longitude: " + longitude +
                ", tag_id: " + tag.getId() + ", severity_id: " + severity.getId() + ", user_id: " + user.getId() +
                ", image_path: " + imagePath + ", description: " + description +
                ", distance: " + distance + "}";
    }

    public EventResponseDto toDto() {
        EventResponseDto dto = new EventResponseDto();
        dto.setId(getId());
        dto.setDateTime(getDateTime());
        dto.setLatitude(getLatitude());
        dto.setLongitude(getLongitude());
        dto.setImagePath(getImagePath());
        dto.setDescription(getDescription());
        dto.setEventComments(getEventComments() == null ?
                null : getEventComments().stream().map(EventComment::toDto).collect(Collectors.toSet()));
        dto.setSeverity(getSeverity() == null ? null : getSeverity().toDto());
        dto.setTag(getTag() == null ? null : getTag().toDto());
        dto.setUser(getUser() == null ? null : getUser().toDto());
        dto.setDistance(getDistance());
        return dto;
    }

}

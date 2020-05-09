package com.as.eventalertbackend.data.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
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
    @OneToOne
    private EventSeverity severity;
    @OneToOne
    private EventTag tag;
    @OneToOne
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

}

package com.as.eventalertbackend.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

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

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false)
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

}

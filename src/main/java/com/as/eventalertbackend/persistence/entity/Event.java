package com.as.eventalertbackend.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "event")
@Getter
@Setter
@NoArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private Double latitude;

    private Double longitude;

    private String imagePath;

    private String description;

    @OneToMany(mappedBy = "event")
    private Set<EventComment> eventComments;

    @ManyToOne
    @JoinColumn(name = "severity_id")
    private EventSeverity severity;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private EventType type;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Transient
    private Double distance;

}

package com.as.eventalertbackend.persistence.entity;

import com.as.eventalertbackend.model.EventTypeCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "event_type")
@Getter
@Setter
@NoArgsConstructor
public class EventType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private EventTypeCategory category;

    @Enumerated(EnumType.STRING)
    private EventTypeCode code;

    private String label;

    private String imagePath;

    @OneToMany(mappedBy = "type")
    private Set<Event> events;

}

package com.as.eventalertbackend.persistence.entity;

import com.as.eventalertbackend.enums.EventSeverityCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "event_severity")
@Getter
@Setter
@NoArgsConstructor
public class EventSeverity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private EventSeverityCode code;

    private String name;

    private Integer color;

    @OneToMany(mappedBy = "severity")
    private Set<Event> events;

}

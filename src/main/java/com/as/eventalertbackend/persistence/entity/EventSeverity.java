package com.as.eventalertbackend.persistence.entity;

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

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer color;

    @OneToMany(mappedBy = "severity")
    private Set<Event> events;

}

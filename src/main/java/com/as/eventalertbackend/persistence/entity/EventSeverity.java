package com.as.eventalertbackend.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "severity")
@Getter
@Setter
@NoArgsConstructor
public class EventSeverity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 3, max = 40, message = "The name must have between 3 and 40 characters")
    private String name;

    private Integer color;

    @OneToMany(mappedBy = "severity")
    private Set<Event> events;

}

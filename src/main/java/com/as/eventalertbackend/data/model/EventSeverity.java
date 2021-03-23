package com.as.eventalertbackend.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "severity")
@Getter
@Setter
public class EventSeverity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 3, max = 40, message = "The name must have between 3 and 40 characters")
    private String name;
    private int color;
    @JsonIgnore
    @OneToMany(mappedBy = "severity")
    private Set<Event> events;

}

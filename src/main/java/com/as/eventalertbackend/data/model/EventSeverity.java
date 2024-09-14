package com.as.eventalertbackend.data.model;

import com.as.eventalertbackend.dto.EventSeverityDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode
public class EventSeverity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 3, max = 40, message = "The name must have between 3 and 40 characters")
    private String name;

    private Integer color;

    @JsonIgnore
    @OneToMany(mappedBy = "severity")
    private Set<Event> events;

    public EventSeverityDto toDto() {
        EventSeverityDto dto = new EventSeverityDto();
        dto.setId(getId());
        dto.setName(getName());
        dto.setColor(getColor());
        return dto;
    }

}

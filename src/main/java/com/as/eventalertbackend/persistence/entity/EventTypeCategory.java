package com.as.eventalertbackend.persistence.entity;

import com.as.eventalertbackend.enums.EventTypeCategoryCode;
import com.as.eventalertbackend.enums.EventTypeCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "event_type_category")
@Getter
@Setter
@NoArgsConstructor
public class EventTypeCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private EventTypeCategoryCode code;

    private String label;

    private String imagePath;

    @OneToMany(mappedBy = "category")
    private Set<EventType> types;

}

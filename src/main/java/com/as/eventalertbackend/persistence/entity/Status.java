package com.as.eventalertbackend.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

import static com.as.eventalertbackend.AppConstants.*;

@Entity
@Table(name = "status") // Lookup table
@Getter
@Setter
@NoArgsConstructor
public class Status {

    @Id
    @Column(length = MAX_LENGTH_50)
    private String id;

    @Column(nullable = false, length = MAX_LENGTH_50)
    private String label;

    @Column(nullable = false, length = FIXED_COLOR_LENGTH)
    private String color;

    @Column(nullable = false, length = MAX_LENGTH_1000)
    private String description;

    @Column(nullable = false, unique = true)
    private Integer position;

    @OneToMany(mappedBy = "status")
    private List<Event> events;

}

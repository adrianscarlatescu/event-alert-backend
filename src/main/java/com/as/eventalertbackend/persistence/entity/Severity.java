package com.as.eventalertbackend.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

import static com.as.eventalertbackend.AppConstants.FIXED_COLOR_LENGTH;
import static com.as.eventalertbackend.AppConstants.MAX_LENGTH_50;

@Entity
@Table(name = "severity") // Lookup table
@Getter
@Setter
@NoArgsConstructor
public class Severity {

    @Id
    @Column(length = MAX_LENGTH_50)
    private String id;

    @Column(nullable = false, length = MAX_LENGTH_50)
    private String label;

    @Column(nullable = false, length = FIXED_COLOR_LENGTH)
    private String color;

    @Column(nullable = false, unique = true)
    private Integer position;

    @OneToMany(mappedBy = "severity")
    private List<Event> events;

}

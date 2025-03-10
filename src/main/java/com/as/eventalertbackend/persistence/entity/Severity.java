package com.as.eventalertbackend.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

import static com.as.eventalertbackend.AppConstants.LENGTH_50;
import static com.as.eventalertbackend.AppConstants.LENGTH_7;

@Entity
@Table(name = "severity") // Lookup table
@Getter
@Setter
@NoArgsConstructor
public class Severity {

    @Id
    @Column(length = LENGTH_50)
    private String id;

    @Column(nullable = false, length = LENGTH_50)
    private String label;

    @Column(nullable = false, length = LENGTH_7)
    private String color;

    @Column(nullable = false, unique = true)
    private Integer position;

    @OneToMany(mappedBy = "severity")
    private List<Event> events;

}

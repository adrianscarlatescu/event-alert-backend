package com.as.eventalertbackend.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

import static com.as.eventalertbackend.AppConstants.LENGTH_50;

@Entity
@Table(name = "severity")
@Getter
@Setter
@NoArgsConstructor
public class Severity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = LENGTH_50)
    private String code; // More flexibility instead of using an enum

    @Column(nullable = false, length = LENGTH_50)
    private String label;

    @Column(nullable = false)
    private Integer color;

    @OneToMany(mappedBy = "severity")
    private List<Event> events;

}

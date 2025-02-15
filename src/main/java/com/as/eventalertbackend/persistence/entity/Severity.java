package com.as.eventalertbackend.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "severity")
@Getter
@Setter
@NoArgsConstructor
public class Severity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code; // More flexibility instead of using an enum

    private String label;

    private Integer color;

    @OneToMany(mappedBy = "severity")
    private List<Event> events;

}

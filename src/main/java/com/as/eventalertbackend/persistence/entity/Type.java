package com.as.eventalertbackend.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

import static com.as.eventalertbackend.AppConstants.LENGTH_1000;
import static com.as.eventalertbackend.AppConstants.LENGTH_50;

@Entity
@Table(name = "type")
@Getter
@Setter
@NoArgsConstructor
public class Type {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = false, unique = true, length = LENGTH_50)
    private String code; // More flexibility instead of using an enum

    @Column(nullable = false, length = LENGTH_50)
    private String label;

    @Column(nullable = false, length = LENGTH_1000)
    private String imagePath;

    @OneToMany(mappedBy = "type")
    private List<Event> events;

}

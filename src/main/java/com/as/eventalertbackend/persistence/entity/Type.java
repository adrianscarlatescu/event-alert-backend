package com.as.eventalertbackend.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

import static com.as.eventalertbackend.AppConstants.LENGTH_1000;
import static com.as.eventalertbackend.AppConstants.LENGTH_50;

@Entity
@Table(name = "ref_type") // Lookup table
@Getter
@Setter
@NoArgsConstructor
public class Type {

    @Id
    @Column(length = LENGTH_50)
    private String id;

    @Column(nullable = false, length = LENGTH_50)
    private String label;

    @Column(nullable = false, length = LENGTH_1000)
    private String imagePath;

    @Column(nullable = false, unique = true)
    private Integer position;

    @OneToMany(mappedBy = "type")
    private List<Event> events;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}

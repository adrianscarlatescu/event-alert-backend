package com.as.eventalertbackend.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

import static com.as.eventalertbackend.AppConstants.MAX_LENGTH_1000;
import static com.as.eventalertbackend.AppConstants.MAX_LENGTH_50;

@Entity
@Table(name = "category") // Lookup table
@Getter
@Setter
@NoArgsConstructor
public class Category {

    @Id
    @Column(length = MAX_LENGTH_50)
    private String id;

    @Column(nullable = false, length = MAX_LENGTH_50)
    private String label;

    @Column(nullable = false, length = MAX_LENGTH_1000)
    private String imagePath;

    @Column(nullable = false, unique = true)
    private Integer position;

    @OneToMany(mappedBy = "category")
    @OrderBy("position asc")
    private List<Type> types;

}

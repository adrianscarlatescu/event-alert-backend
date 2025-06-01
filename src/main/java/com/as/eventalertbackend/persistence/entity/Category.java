package com.as.eventalertbackend.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

import static com.as.eventalertbackend.AppConstants.LENGTH_50;

@Entity
@Table(name = "ref_category") // Lookup table
@Getter
@Setter
@NoArgsConstructor
public class Category {

    @Id
    @Column(length = LENGTH_50)
    private String id;

    @Column(nullable = false, length = LENGTH_50)
    private String label;

    @Column(nullable = false, unique = true)
    private Integer position;

    @OneToMany(mappedBy = "category")
    @OrderBy("position asc")
    private List<Type> types;

}

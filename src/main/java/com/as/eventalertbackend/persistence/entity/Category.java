package com.as.eventalertbackend.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

import static com.as.eventalertbackend.AppConstants.MAX_LENGTH_1000;
import static com.as.eventalertbackend.AppConstants.MAX_LENGTH_50;

@Entity
@Table(name = "category")
@Getter
@Setter
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = MAX_LENGTH_50)
    private String code; // More flexibility instead of using an enum

    @Column(nullable = false, length = MAX_LENGTH_50)
    private String label;

    @Column(nullable = false, length = MAX_LENGTH_1000)
    private String imagePath;

    @OneToMany(mappedBy = "category")
    @OrderBy("label asc")
    private List<Type> types;

}

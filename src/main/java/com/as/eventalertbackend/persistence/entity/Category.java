package com.as.eventalertbackend.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "category")
@Getter
@Setter
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code; // More flexibility instead of using an enum

    private String label;

    private String imagePath;

    @OneToMany(mappedBy = "category")
    @OrderBy("label asc")
    private List<Type> types;

}

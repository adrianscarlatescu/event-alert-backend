package com.as.eventalertbackend.persistence.entity;

import com.as.eventalertbackend.enums.id.GenderId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static com.as.eventalertbackend.AppConstants.LENGTH_50;

@Entity
@Table(name = "gender") // Lookup table
@Getter
@Setter
@NoArgsConstructor
public class Gender {

    @Id
    @Column(length = LENGTH_50)
    @Enumerated(EnumType.STRING)
    private GenderId id;

    @Column(nullable = false, length = LENGTH_50)
    private String label;

    @Column(nullable = false, unique = true)
    private Integer position;

}

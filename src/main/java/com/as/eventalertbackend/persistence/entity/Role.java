package com.as.eventalertbackend.persistence.entity;

import com.as.eventalertbackend.enums.id.RoleId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

import static com.as.eventalertbackend.AppConstants.LENGTH_1000;
import static com.as.eventalertbackend.AppConstants.LENGTH_50;

@Entity
@Table(name = "role") // Lookup table
@Getter
@Setter
@NoArgsConstructor
public class Role {

    @Id
    @Column(length = LENGTH_50)
    @Enumerated(EnumType.STRING)
    private RoleId id;

    @Column(nullable = false, length = LENGTH_50)
    private String label;

    @Column(nullable = false, length = LENGTH_1000)
    private String description;

    @Column(nullable = false, unique = true)
    private Integer position;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

}

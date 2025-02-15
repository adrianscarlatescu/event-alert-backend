package com.as.eventalertbackend.persistence.entity;

import com.as.eventalertbackend.model.RoleCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "role")
@Getter
@Setter
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleCode code;

    private String label;

    private String description;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

}

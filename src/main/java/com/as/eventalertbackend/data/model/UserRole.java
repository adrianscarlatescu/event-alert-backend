package com.as.eventalertbackend.data.model;

import com.as.eventalertbackend.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "role")
@Getter
@Setter
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Role name;
    @JsonIgnore
    @ManyToMany(mappedBy = "userRoles")
    private Set<User> users;

}

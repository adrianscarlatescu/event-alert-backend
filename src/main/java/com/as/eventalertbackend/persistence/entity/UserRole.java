package com.as.eventalertbackend.persistence.entity;

import com.as.eventalertbackend.enums.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user_role")
@Getter
@Setter
@NoArgsConstructor
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Role name;

    private String description;

    @ManyToMany(mappedBy = "userRoles")
    private Set<User> users;

}

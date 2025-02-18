package com.as.eventalertbackend.persistence.entity;

import com.as.eventalertbackend.enums.RoleCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

import static com.as.eventalertbackend.AppConstants.MAX_LENGTH_1000;
import static com.as.eventalertbackend.AppConstants.MAX_LENGTH_50;

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
    @Column(nullable = false, length = MAX_LENGTH_50)
    private RoleCode code;

    @Column(nullable = false, length = MAX_LENGTH_50)
    private String label;

    @Column(nullable = false, length = MAX_LENGTH_1000)
    private String description;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

}

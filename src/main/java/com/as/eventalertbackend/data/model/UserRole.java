package com.as.eventalertbackend.data.model;

import com.as.eventalertbackend.dto.UserRoleDto;
import com.as.eventalertbackend.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "role")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Role name;

    @JsonIgnore
    @ManyToMany(mappedBy = "userRoles")
    private Set<User> users;

    public UserRoleDto toDto() {
        UserRoleDto dto = new UserRoleDto();
        dto.setId(getId());
        dto.setName(getName());
        return dto;
    }

}

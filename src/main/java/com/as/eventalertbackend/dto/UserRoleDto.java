package com.as.eventalertbackend.dto;

import com.as.eventalertbackend.enums.Role;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class UserRoleDto implements Serializable {

    private Long id;
    private Role name;

}

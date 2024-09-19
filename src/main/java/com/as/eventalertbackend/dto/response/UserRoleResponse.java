package com.as.eventalertbackend.dto.response;

import com.as.eventalertbackend.enums.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class UserRoleResponse implements Serializable {

    private Long id;
    private Role name;

}

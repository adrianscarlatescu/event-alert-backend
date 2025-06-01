package com.as.eventalertbackend.dto.role;

import com.as.eventalertbackend.enums.id.RoleId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class RoleDTO implements Serializable {

    private RoleId id;
    private String label;
    private String description;
    private Integer position;

}

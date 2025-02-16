package com.as.eventalertbackend.dto.role;

import com.as.eventalertbackend.enums.RoleCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class RoleDTO implements Serializable {

    private Long id;
    private RoleCode code;
    private String label;
    private String description;

}

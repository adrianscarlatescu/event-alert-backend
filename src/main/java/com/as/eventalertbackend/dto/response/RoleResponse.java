package com.as.eventalertbackend.dto.response;

import com.as.eventalertbackend.model.RoleCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class RoleResponse implements Serializable {

    private Long id;
    private RoleCode code;
    private String label;
    private String description;


}

package com.as.eventalertbackend.dto.gender;

import com.as.eventalertbackend.enums.id.GenderId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class GenderDTO implements Serializable {

    private GenderId id;
    private String label;
    private Integer position;


}

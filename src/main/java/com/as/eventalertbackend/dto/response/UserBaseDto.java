package com.as.eventalertbackend.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserBaseDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String imagePath;

}

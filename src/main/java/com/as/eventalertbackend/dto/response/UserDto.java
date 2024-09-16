package com.as.eventalertbackend.dto.response;

import com.as.eventalertbackend.enums.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserDto implements Serializable {

    private Long id;
    private LocalDateTime joinDateTime;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String imagePath;
    private Gender gender;
    private Set<UserRoleDto> userRoles;
    private int reportsNumber;

}

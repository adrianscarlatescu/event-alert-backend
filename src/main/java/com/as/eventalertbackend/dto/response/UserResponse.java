package com.as.eventalertbackend.dto.response;

import com.as.eventalertbackend.model.GenderCode;
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
public class UserResponse implements Serializable {

    private Long id;
    private LocalDateTime joinedAt;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String imagePath;
    private GenderCode genderCode;
    private Set<UserRoleResponse> userRoles;
    private Integer reportsNumber;

}

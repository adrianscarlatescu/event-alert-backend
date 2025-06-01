package com.as.eventalertbackend.dto.user;

import com.as.eventalertbackend.dto.role.RoleDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO implements Serializable {

    private Long id;
    private LocalDateTime joinedAt;
    private LocalDateTime modifiedAt;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String imagePath;
    private List<RoleDTO> roles;
    private Integer reportsNumber;

}

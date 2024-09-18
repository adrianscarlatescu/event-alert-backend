package com.as.eventalertbackend.dto.request;

import com.as.eventalertbackend.AppConstants;
import com.as.eventalertbackend.enums.Gender;
import com.as.eventalertbackend.enums.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserRequestDto {

    private String firstName;

    private String lastName;

    @Past(message = "Invalid date of birth")
    private LocalDate dateOfBirth;

    @Pattern(regexp = AppConstants.PHONE_NUMBER_PATTERN, message = "The phone number does not match the expected format")
    private String phoneNumber;

    private String imagePath;

    private Gender gender;

    @NotEmpty(message = "Minimum one role is required")
    private Set<Role> roles;

}

package com.as.eventalertbackend.dto.request;

import com.as.eventalertbackend.AppConstants;
import com.as.eventalertbackend.model.GenderCode;
import com.as.eventalertbackend.model.RoleCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserRequest {

    @Size(max = AppConstants.MAX_USER_NAME_LENGTH,
            message = "The first name must not exceed " + AppConstants.MAX_USER_NAME_LENGTH + " characters")
    private String firstName;

    @Size(max = AppConstants.MAX_USER_NAME_LENGTH,
            message = "The last name must not exceed " + AppConstants.MAX_USER_NAME_LENGTH + " characters")
    private String lastName;

    @Past(message = "The date of birth must be in the past")
    private LocalDate dateOfBirth;

    @NotBlank(message = "The phone number is mandatory")
    @Pattern(regexp = AppConstants.PHONE_NUMBER_PATTERN, message = "The phone number does not match the expected format")
    private String phoneNumber;

    private String imagePath;

    private GenderCode genderCode;

    @NotEmpty(message = "At least one role is required")
    private Set<RoleCode> roleCodes;

}

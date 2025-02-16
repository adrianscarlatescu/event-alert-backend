package com.as.eventalertbackend.dto.user;

import com.as.eventalertbackend.model.GenderCode;
import com.as.eventalertbackend.model.RoleCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

import static com.as.eventalertbackend.AppConstants.MAX_DEFAULT_LENGTH;
import static com.as.eventalertbackend.AppConstants.PHONE_NUMBER_PATTERN;

@Getter
@Setter
@NoArgsConstructor
public class UserUpdateDTO {

    @Size(max = MAX_DEFAULT_LENGTH, message = "The first name must not exceed " + MAX_DEFAULT_LENGTH + " characters")
    private String firstName;

    @Size(max = MAX_DEFAULT_LENGTH, message = "The last name must not exceed " + MAX_DEFAULT_LENGTH + " characters")
    private String lastName;

    @Past(message = "The date of birth must be in the past")
    private LocalDate dateOfBirth;

    @Pattern(regexp = PHONE_NUMBER_PATTERN, message = "The phone number does not match the expected format")
    private String phoneNumber;

    private String imagePath;

    private GenderCode genderCode;

    @NotEmpty(message = "At least one role is required")
    private List<RoleCode> roleCodes;

}

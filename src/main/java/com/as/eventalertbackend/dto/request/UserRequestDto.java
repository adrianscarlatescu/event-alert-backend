package com.as.eventalertbackend.dto.request;

import com.as.eventalertbackend.AppConstants;
import com.as.eventalertbackend.enums.Gender;
import com.as.eventalertbackend.enums.Role;
import com.as.eventalertbackend.handler.ApiErrorValidationMessage;
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

    @Past(message = ApiErrorValidationMessage.INVALID_DATE_OF_BIRTH)
    private LocalDate dateOfBirth;

    @Pattern(regexp = AppConstants.PHONE_NUMBER_PATTERN, message = ApiErrorValidationMessage.INVALID_PHONE_FORMAT)
    private String phoneNumber;

    private String imagePath;

    private Gender gender;

    @NotEmpty(message = ApiErrorValidationMessage.MIN_ROLE_REQUIRED)
    private Set<Role> roles;

}

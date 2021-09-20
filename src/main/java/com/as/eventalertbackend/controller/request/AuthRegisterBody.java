package com.as.eventalertbackend.controller.request;

import com.as.eventalertbackend.AppConstants;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class AuthRegisterBody {

    @Email(message = "Invalid email")
    @NotEmpty(message = "The email is mandatory")
    private String email;

    @NotNull(message = "The password is mandatory")
    @Size(min = AppConstants.MIN_PASSWORD_LENGTH, max = AppConstants.MAX_PASSWORD_LENGTH,
            message = "The password must have between " +
                    AppConstants.MIN_PASSWORD_LENGTH + " and " +
                    AppConstants.MAX_PASSWORD_LENGTH + " characters")
    private String password;
    private String confirmPassword;

}

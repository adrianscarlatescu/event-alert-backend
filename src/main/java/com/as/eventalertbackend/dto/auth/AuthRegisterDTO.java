package com.as.eventalertbackend.dto.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

import static com.as.eventalertbackend.AppConstants.*;

@Getter
@Setter
@NoArgsConstructor
public class AuthRegisterDTO implements Serializable {

    @Email(message = "Invalid email")
    @NotBlank(message = "The email is mandatory")
    @Size(max = MAX_DEFAULT_LENGTH, message = "The email must not exceed " + MAX_DEFAULT_LENGTH + " characters")
    private String email;

    @NotBlank(message = "The password is mandatory")
    @Size(min = MIN_PASSWORD_LENGTH, max = MAX_PASSWORD_LENGTH,
            message = "The password must have between " + MIN_PASSWORD_LENGTH + " and " + MAX_PASSWORD_LENGTH + " characters")
    private String password;

    @NotBlank(message = "The confirmation password is mandatory")
    private String confirmPassword;

}

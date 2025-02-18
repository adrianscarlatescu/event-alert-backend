package com.as.eventalertbackend.dto.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

import static com.as.eventalertbackend.AppConstants.MAX_LENGTH_50;
import static com.as.eventalertbackend.AppConstants.MIN_LENGTH_8;

@Getter
@Setter
@NoArgsConstructor
public class AuthRegisterDTO implements Serializable {

    @NotBlank(message = "The email is mandatory")
    @Email(message = "Invalid email")
    @Size(max = MAX_LENGTH_50, message = "The email must not exceed " + MAX_LENGTH_50 + " characters")
    private String email;

    @NotBlank(message = "The password is mandatory")
    @Size(min = MIN_LENGTH_8, max = MAX_LENGTH_50,
            message = "The password must have between " + MIN_LENGTH_8 + " and " + MAX_LENGTH_50 + " characters")
    private String password;

    @NotBlank(message = "The confirmation password is mandatory")
    private String confirmPassword;

}

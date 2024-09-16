package com.as.eventalertbackend.dto.request;

import com.as.eventalertbackend.AppConstants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class AuthRegisterRequestDto implements Serializable {

    @Email(message = "Invalid email")
    @NotBlank(message = "The email is mandatory")
    private String email;

    @NotBlank(message = "The password is mandatory")
    @Size(min = AppConstants.MIN_PASSWORD_LENGTH, max = AppConstants.MAX_PASSWORD_LENGTH,
            message = "The password must have between " +
                    AppConstants.MIN_PASSWORD_LENGTH + " and " +
                    AppConstants.MAX_PASSWORD_LENGTH + " characters")
    private String password;

    @NotBlank(message = "The password must be confirmed")
    private String confirmPassword;

}

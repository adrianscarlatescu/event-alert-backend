package com.as.eventalertbackend.dto.request;

import com.as.eventalertbackend.AppConstants;
import com.as.eventalertbackend.handler.ApiErrorValidationMessage;
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

    @Email(message = ApiErrorValidationMessage.INVALID_EMAIL)
    @NotBlank(message = ApiErrorValidationMessage.MANDATORY_EMAIL)
    private String email;

    @NotBlank(message = ApiErrorValidationMessage.MANDATORY_PASSWORD)
    @Size(min = AppConstants.MIN_PASSWORD_LENGTH,
            max = AppConstants.MAX_PASSWORD_LENGTH,
            message = ApiErrorValidationMessage.PASSWORD_LENGTH)
    private String password;

    @NotBlank(message = ApiErrorValidationMessage.PASSWORD_CONFIRMATION)
    private String confirmPassword;

}

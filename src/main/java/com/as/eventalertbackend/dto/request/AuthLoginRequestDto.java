package com.as.eventalertbackend.dto.request;

import com.as.eventalertbackend.handler.ApiErrorValidationMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class AuthLoginRequestDto implements Serializable {

    @Email(message = ApiErrorValidationMessage.INVALID_EMAIL)
    @NotBlank(message = ApiErrorValidationMessage.MANDATORY_EMAIL)
    private String email;

    @NotBlank(message = ApiErrorValidationMessage.MANDATORY_PASSWORD)
    private String password;

}

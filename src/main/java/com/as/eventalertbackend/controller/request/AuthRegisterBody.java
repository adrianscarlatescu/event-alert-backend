package com.as.eventalertbackend.controller.request;

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
    @Size(min = 8, max = 40, message = "The password must have between 8 and 40 characters")
    private String password;
    private String confirmPassword;

}
